package com.vending.machine.domain.model;

import com.vending.machine.application.exception.InvalidRoleException;

import java.util.Arrays;

public enum RoleType {
    SELLER, BUYER;

    public static RoleType fromString(String roleTypeString) {
        return Arrays.stream(values())
                .filter(roleType -> roleType.name().equals(roleTypeString))
                .findFirst()
                .orElseThrow(() -> new InvalidRoleException(roleTypeString));
    }
}
