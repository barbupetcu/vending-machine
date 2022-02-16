package com.vending.machine.application.service.user;

import com.vending.machine.api.model.user.CreateUserRequest;
import com.vending.machine.api.model.user.UserResponse;
import com.vending.machine.application.model.ChangePasswordCommand;
import com.vending.machine.application.model.ChangeRolesCommand;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    void changePassword(ChangePasswordCommand changePasswordCommand);

    void changeRoles(ChangeRolesCommand changeRolesCommand);
}
