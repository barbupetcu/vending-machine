package com.vending.machine.application.service;

import com.vending.machine.api.model.user.ChangeUserRolesRequest;
import com.vending.machine.application.exception.UserAlreadyExistsException;
import com.vending.machine.api.model.user.ChangeUserPasswordRequest;
import com.vending.machine.api.model.user.CreateUserRequest;
import com.vending.machine.api.model.user.UserResponse;
import com.vending.machine.application.exception.UserNotFoundException;
import com.vending.machine.application.mapper.UserMapper;
import com.vending.machine.domain.model.User;
import com.vending.machine.domain.model.UserRole;
import com.vending.machine.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException(request.getUsername());
        }
        User user = userRepository.save(UserMapper.build(request));
        return UserResponse.builder()
                .userId(user.getId())
                .timestamp(user.getCreatedAt())
                .roles(request.getRoles())
                .build();
    }

    @Override
    @Transactional
    public void changePassword(Long userId, ChangeUserPasswordRequest request) {
        //todo impl with password encoder after spring security integration
    }

    @Override
    @Transactional
    public void changeRoles(Long userId, ChangeUserRolesRequest request) {
        User user = userRepository.findByIdWithRoles(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        getUserRolesToRemove(request, user).forEach(user::removeRole);
        getUserRolesToAdd(request, user).forEach(user::addRole);
        userRepository.save(user);
    }

    private List<UserRole> getUserRolesToRemove(ChangeUserRolesRequest request, User user) {
        return user.getRoles().stream()
                .filter(existingRole -> !request.getRoles().contains(existingRole.getRole()))
                .collect(Collectors.toList());
    }

    private List<UserRole> getUserRolesToAdd(ChangeUserRolesRequest request, User user) {
        return request.getRoles().stream()
                .filter(
                        requestRole -> user.getRoles().stream()
                                .map(UserRole::getRole)
                                .noneMatch(requestRole::equals)
                )
                .map(UserRole::fromRole)
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}