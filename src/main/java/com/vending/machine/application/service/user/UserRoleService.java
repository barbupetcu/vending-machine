package com.vending.machine.application.service.user;

import com.vending.machine.application.service.AuthenticationService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    public static final String IS_SELLER = "@userRoleService.hasRole(principal, 'SELLER')";
    public static final String IS_BUYER = "@userRoleService.hasRole(principal, 'BUYER')";

    public boolean hasRole(Jwt jwt, String roleType) {
        String userRoles = (String) jwt.getClaims().get(AuthenticationService.ROLE_CLAIM);
        for (String role : userRoles.split(",")) {
            if (roleType.equals(role)) {
                return true;
            }
        }
        return false;
    }

}
