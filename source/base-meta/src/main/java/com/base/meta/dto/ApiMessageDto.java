package com.base.meta.dto;

import lombok.Data;

@Data
public class ApiMessageDto<T> {
    private Boolean result = true;
    private String code = null;
    private T data = null;
    private String message = null;
    private String firebaseUrl;
    private String urlBase;
}
