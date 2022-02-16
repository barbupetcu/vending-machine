package com.vending.machine.application.model;

import com.vending.machine.domain.model.RoleType;
import lombok.Getter;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ChangeRolesCommand extends UserRequest {

    private final List<RoleType> roles;

    public ChangeRolesCommand(Authentication authentication, List<String> roles) {
        super(UserRequest.getUserId(authentication));
        this.roles = roles.stream()
                .map(RoleType::fromString)
                .collect(Collectors.toList());
    }
}
