package com.base.meta.controller;

import com.base.meta.constant.BaseMetaConstant;
import com.base.meta.jwt.BaseMetaJwt;
import com.base.meta.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.Objects;

public class ABasicController {
    @Autowired
    private UserServiceImpl userService;

    public long getCurrentUser() {
        BaseMetaJwt baseMetaJwt = userService.getAddInfoFromToken();
        return baseMetaJwt.getAccountId();
    }

    public long getTokenId() {
        BaseMetaJwt baseMetaJwt = userService.getAddInfoFromToken();
        return baseMetaJwt.getTokenId();
    }

    public BaseMetaJwt getSessionFromToken() {
        return userService.getAddInfoFromToken();
    }

    public boolean isSuperAdmin() {
        BaseMetaJwt baseMetaJwt = userService.getAddInfoFromToken();
        if (baseMetaJwt != null) {
            return baseMetaJwt.getIsSuperAdmin();
        }
        return false;
    }

    public String getCurrentToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            OAuth2AuthenticationDetails oauthDetails =
                    (OAuth2AuthenticationDetails) authentication.getDetails();
            if (oauthDetails != null) {
                return oauthDetails.getTokenValue();
            }
        }
        return null;
    }

    public boolean isPM() {
        BaseMetaJwt baseMetaJwt = userService.getAddInfoFromToken();
        if (baseMetaJwt != null) {
            return Objects.equals(baseMetaJwt.getUserKind(), BaseMetaConstant.USER_KIND_PM);
        }
        return false;
    }

    public boolean isDev() {
        BaseMetaJwt baseMetaJwt = userService.getAddInfoFromToken();
        if (baseMetaJwt != null) {
            return Objects.equals(baseMetaJwt.getUserKind(), BaseMetaConstant.USER_KIND_DEV);
        }
        return false;
    }

    public boolean isTester() {
        BaseMetaJwt baseMetaJwt = userService.getAddInfoFromToken();
        if (baseMetaJwt != null) {
            return Objects.equals(baseMetaJwt.getUserKind(), BaseMetaConstant.USER_KIND_TESTER);
        }
        return false;
    }
}
