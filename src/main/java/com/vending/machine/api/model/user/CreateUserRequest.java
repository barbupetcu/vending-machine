package com.vending.machine.api.model.user;

import com.vending.machine.domain.model.RoleType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class CreateUserRequest {

    @NotBlank(message = "username is mandatory")
    private String username;
    @NotBlank(message = "password is mandatory")
    private String password;
    @NotEmpty(message = "at least one role is mandatory")
    private List<RoleType> roles;
}
