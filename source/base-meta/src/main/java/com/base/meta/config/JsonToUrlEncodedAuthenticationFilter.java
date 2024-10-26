package com.base.meta.config;

import com.google.common.io.ByteStreams;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.web.savedrequest.Enumerator;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JsonToUrlEncodedAuthenticationFilter extends OncePerRequestFilter {
    /**
     * Filter to convert json request to url encoded request
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Token request content type: {}", request.getContentType());

        // Check if the request is for the token endpoint and is of type JSON
        if ("/api/token".equals(request.getServletPath()) &&
                MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())) {

            // Read the JSON request body
            byte[] json = ByteStreams.toByteArray(request.getInputStream());

            // Convert JSON to Map
            Map<String, String> jsonMap = new ObjectMapper().readValue(json, Map.class);

            // Convert the Map to String[] parameters for the HttpServletRequest
            Map<String, String[]> parameters = jsonMap.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> new String[]{e.getValue()}
                    ));

            // Wrap the original request with the new parameters
            HttpServletRequest requestWrapper = new RequestWrapper(request, parameters);
            filterChain.doFilter(requestWrapper, response); // Continue with the wrapped request
        } else {
            // Proceed with the original request if it doesn't match the condition
            filterChain.doFilter(request, response);
        }
    }


    private class RequestWrapper extends HttpServletRequestWrapper {

        private final Map<String, String[]> params;

        RequestWrapper(HttpServletRequest request, Map<String, String[]> params) {
            super(request);
            this.params = params;
        }

        @Override
        public String getParameter(String name) {
            if (this.params.containsKey(name)) {
                return this.params.get(name)[0];
            }
            return "";
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return this.params;
        }

        @Override
        public Enumeration<String> getParameterNames() {
            return new Enumerator<>(params.keySet());
        }

        @Override
        public String[] getParameterValues(String name) {
            return params.get(name);
        }
    }
}
