package com.vending.machine.application.mapper;

import com.vending.machine.api.model.user.CreateUserRequest;
import com.vending.machine.domain.model.User;
import com.vending.machine.domain.model.UserRole;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class UserMapper {

    public User build(CreateUserRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .deposit(BigDecimal.ZERO)
                .build();
        request.getRoles().stream()
                .map(UserRole::fromRole)
                .forEach(user::addRole);
        return user;
    }
}