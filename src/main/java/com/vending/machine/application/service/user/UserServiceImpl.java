package com.vending.machine.application.service.user;

import com.vending.machine.api.model.user.CreateUserRequest;
import com.vending.machine.api.model.user.UserResponse;
import com.vending.machine.application.exception.OldPasswordNotValidException;
import com.vending.machine.application.exception.UserAlreadyExistsException;
import com.vending.machine.application.exception.UserNotFoundException;
import com.vending.machine.application.mapper.UserMapper;
import com.vending.machine.application.model.ChangePasswordCommand;
import com.vending.machine.application.model.ChangeRolesCommand;
import com.vending.machine.domain.model.User;
import com.vending.machine.domain.model.UserRole;
import com.vending.machine.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException(request.getUsername());
        }
        User user = userRepository.save(UserMapper.build(request, passwordEncoder));
        return UserResponse.builder()
                .userId(user.getId())
                .timestamp(user.getCreatedAt())
                .roles(request.getRoles())
                .build();
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordCommand changePasswordCommand) {
        User user = userRepository.findById(changePasswordCommand.getUserId())
                .orElseThrow(() -> new UserNotFoundException(changePasswordCommand.getUserId()));
        if (passwordEncoder.matches(changePasswordCommand.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordCommand.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new OldPasswordNotValidException(user.getUsername());
        }
    }

    @Override
    @Transactional
    public void changeRoles(ChangeRolesCommand changeRolesCommand) {
        User user = userRepository.findByIdWithRoles(changeRolesCommand.getUserId())
                .orElseThrow(() -> new UserNotFoundException(changeRolesCommand.getUserId()));
        getUserRolesToRemove(changeRolesCommand, user).forEach(user::removeRole);
        getUserRolesToAdd(changeRolesCommand, user).forEach(user::addRole);
        userRepository.save(user);
    }

    private List<UserRole> getUserRolesToRemove(ChangeRolesCommand request, User user) {
        return user.getRoles().stream()
                .filter(existingRole -> !request.getRoles().contains(existingRole.getRole()))
                .collect(Collectors.toList());
    }

    private List<UserRole> getUserRolesToAdd(ChangeRolesCommand request, User user) {
        return request.getRoles().stream()
                .filter(
                        requestRole -> user.getRoles().stream()
                                .map(UserRole::getRole)
                                .noneMatch(requestRole::equals)
                )
                .map(UserRole::fromRole)
                .collect(Collectors.toList());
    }
}