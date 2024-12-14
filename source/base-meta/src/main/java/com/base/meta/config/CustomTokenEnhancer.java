package com.base.meta.config;

import com.base.meta.dto.AccountForTokenDto;
import com.base.meta.model.TablePrefix;
import com.base.meta.utils.ZipUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class CustomTokenEnhancer implements TokenEnhancer {
    private JdbcTemplate jdbcTemplate;

    private ObjectMapper objectMapper;

    public CustomTokenEnhancer(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo;
        String grantType = authentication.getOAuth2Request().getRequestParameters().get("grantType");
        String userId = authentication.getOAuth2Request().getRequestParameters().get("userId");
        String username = authentication.getName();
        if (Objects.equals(grantType, SecurityConstant.GRANT_TYPE_COMPANY)) {
            additionalInfo = getAdditionalInfoTypePass(username, grantType, Long.valueOf(userId));
        } else {
            additionalInfo = getAdditionalInfoTypePass(username, grantType, null);
        }
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }

    private Map<String, Object> getAdditionalInfoTypePass(String username, String grantType, Long userId) {
        Map<String, Object> additionalInfo = new HashMap<>();
        AccountForTokenDto a = getAccountByUsername(username);

        if (a != null) {
            Long accountId = a.getId();
            boolean b = grantType != null && (grantType.equals(SecurityConstant.GRANT_TYPE_COMPANY));
            if (b) {
                accountId = userId;
            }
            Long storeId = -1L;
            String kind = a.getKind() + "";//token kind
            Long deviceId = -1L;// id cua thiet bi, lưu ở table device để get firebase url..
            String permission = "<>";//empty string
            Integer userKind = a.getKind(); // kind user là admin hay là gì
            Integer tabletKind = -1;
            Long orderId = -1L;
            Boolean isSuperAdmin = a.getIsSuperAdmin();
            additionalInfo.put("user_id", accountId);
            additionalInfo.put("user_kind", a.getKind());
            additionalInfo.put("grant_type", grantType == null ? SecurityConstant.GRANT_TYPE_PASSWORD : grantType);
            String DELIM = "|";
            String additionalInfoStr = ZipUtils.zipString(accountId + DELIM
                    + storeId + DELIM
                    + kind + DELIM
                    + permission + DELIM
                    + deviceId + DELIM
                    + userKind + DELIM
                    + username + DELIM
                    + tabletKind + DELIM
                    + orderId + DELIM
                    + isSuperAdmin + DELIM);
            additionalInfo.put("additional_info", additionalInfoStr);
        }
        return additionalInfo;
    }

    public AccountForTokenDto getAccountByUsername(String username) {
        try {
            String query = "SELECT id, kind, username, email, full_name, is_super_admin " +
                    "FROM " + TablePrefix.PREFIX_TABLE + "account WHERE username = ? and flag = 1 limit 1;";
            log.debug(query);
            List<AccountForTokenDto> dto = jdbcTemplate.query(query, new Object[]{username}, new BeanPropertyRowMapper<>(AccountForTokenDto.class));
            if (dto.size() > 0) return dto.get(0);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
