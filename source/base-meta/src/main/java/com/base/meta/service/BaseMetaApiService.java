package com.base.meta.service;

import com.base.meta.model.Permission;
import com.base.meta.model.TestCaseUpload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class BaseMetaApiService {
    @Autowired
    BaseMetaOTPService baseMetaOTPService;
    @Autowired
    CommonAsyncService commonAsyncService;
    @Autowired
    ExcelService excelService;
    private final AtomicInteger orderStt = new AtomicInteger(0);
    private String currentDate = getFormattedDate(new Date());

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

    public synchronized void sendOTPEmail(String email, String otp, int time) {
        commonAsyncService.sendOTPEmail(email, otp, time);
    }
    public synchronized void sendPasswordEmail(String email, String fullName, String username, String password) {
        commonAsyncService.sendPasswordEmail(email, fullName, username, password);
    }

    public String generateDisplayId(String prefix, Date date){
        StringBuilder displayId = new StringBuilder();
        displayId.append(prefix);
        displayId.append("_");
        String formattedDate = getFormattedDate(date);
        if (!formattedDate.equals(currentDate)) {
            currentDate = formattedDate;
            orderStt.set(0);
        }
        displayId.append(formattedDate);
        displayId.append("_");
        String formattedOrderStt = String.format("%04d", orderStt.incrementAndGet());
        displayId.append(formattedOrderStt);
        return displayId.toString();
    }
    private String getFormattedDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        return sdf.format(date);
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


}
