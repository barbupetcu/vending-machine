package com.vending.machine.application.mapper;

import com.vending.machine.api.model.user.CreateUserRequest;
import com.vending.machine.domain.model.User;
import com.vending.machine.domain.model.UserRole;
import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@UtilityClass
public class UserMapper {

    public User build(CreateUserRequest request, PasswordEncoder passwordEncoder) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .deposit(0)
                .build();
        request.getRoles().stream()
                .map(UserRole::fromRole)
                .forEach(user::addRole);
        return user;
    }
}
