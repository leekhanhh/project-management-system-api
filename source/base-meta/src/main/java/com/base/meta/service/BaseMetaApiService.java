package com.base.meta.service;

import com.base.meta.model.Permission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class BaseMetaApiService {
    @Autowired
    BaseMetaOTPService baseMetaOTPService;

    @Autowired
    CommonAsyncService commonAsyncService;

    private Map<String, Long> storeQRCodeRandom = new ConcurrentHashMap<>();

    public void deleteFile(String filePath) {
        //call to mediaService for delete

    }


    public String getOTPForgetPassword() {
        return baseMetaOTPService.generate(4);
    }

    public synchronized Long getOrderHash() {
        return Long.parseLong(baseMetaOTPService.generate(9)) + System.currentTimeMillis();
    }

    public void sendEmail(String email, String msg, String subject, boolean html) {
        commonAsyncService.sendEmail(email, msg, subject, html);
    }

    public String convertGroupToUri(List<Permission> permissions) {
        if (permissions != null) {
            StringBuilder builderPermission = new StringBuilder();
            for (Permission p : permissions) {
                builderPermission.append(p.getAction().trim().replace("/v1", "") + ",");
            }
            return builderPermission.toString();
        }
        return null;
    }

    public String getOrderStt(Long storeId) {
        return baseMetaOTPService.orderStt(storeId);
    }

    public synchronized boolean checkCodeValid(String code) {
        //delelete key has valule > 60s
        Set<String> keys = storeQRCodeRandom.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Long value = storeQRCodeRandom.get(key);
            if ((System.currentTimeMillis() - value) > 60000) {
                storeQRCodeRandom.remove(key);
            }
        }

        if (storeQRCodeRandom.containsKey(code)) {
            return false;
        }
        storeQRCodeRandom.put(code, System.currentTimeMillis());
        return true;
    }

    public Boolean checkStartDateIsBeforeEndDate(Date startDate, Date endDate) {
        return startDate.compareTo(endDate) < 0;
    }
    public Boolean checkStartDateIsAfterNow(Date startDate) {
        return startDate.compareTo(new Date()) >= 0;
    }
    public Boolean checkEndDateIsAfterNow(Date endDate) {
        return endDate.compareTo(new Date()) >= 0;
    }


}
