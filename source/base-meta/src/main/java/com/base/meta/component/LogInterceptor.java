package com.base.meta.component;

import com.base.meta.dto.ApiMessageDto;
import com.base.meta.service.LoggingService;
import com.base.meta.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    LoggingService loggingService;
    @Autowired
    UserServiceImpl userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws IOException {
        if (DispatcherType.REQUEST.name().equals(request.getDispatcherType().name())
                && request.getMethod().equals(HttpMethod.GET.name())) {
            loggingService.logRequest(request, null);
        }
        String tenantName = request.getHeader("X-tenant");
        if (tenantName != null) {
            String tenantInfo = userService.getTenantInfo();
            if (tenantInfo != null) {
                List<String> tenantContextList = Arrays.asList(tenantInfo.split(":"));
                for (String tenantContext : tenantContextList) {
                    if (tenantContext.split("&")[0].equals(tenantName)) {
                        String serviceId = tenantContext.split("&")[1];
                        if (serviceId != null) {
                            userService.tenantId.set(serviceId);
                            return true;
                        }
                    }
                }
                ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
                apiMessageDto.setResult(false);
                apiMessageDto.setMessage("Invalid tenant");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, mapper.writeValueAsString(apiMessageDto));
                return false;
            }
        }
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        log.info("Starting call url: [" + getUrl(request) + "]");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        log.debug("Complete [" + getUrl(request) + "] executeTime : " + executeTime + "ms");
        if (ex != null) {
            log.error("afterCompletion>> " + ex.getMessage());
        }
    }

    /**
     * get full url request
     *
     * @param req
     * @return
     */
    private static String getUrl(HttpServletRequest req) {
        String reqUrl = req.getRequestURL().toString();
        String queryString = req.getQueryString();   // d=789
        if (!StringUtils.isEmpty(queryString)) {
            reqUrl += "?" + queryString;
        }
        return reqUrl;
    }
}
