package com.vending.machine.application.service;

import com.vending.machine.api.model.user.ChangeUserPasswordRequest;
import com.vending.machine.api.model.user.ChangeUserRolesRequest;
import com.vending.machine.api.model.user.CreateUserRequest;
import com.vending.machine.api.model.user.UserResponse;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    void changePassword(String userName, ChangeUserPasswordRequest request);

    void changeRoles(Long userId, ChangeUserRolesRequest request);

    void deleteUser(Long userId);
}
