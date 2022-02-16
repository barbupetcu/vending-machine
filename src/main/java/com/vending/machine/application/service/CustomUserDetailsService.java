package com.vending.machine.application.service;

import com.vending.machine.domain.model.RoleType;
import com.vending.machine.domain.model.User;
import com.vending.machine.domain.model.UserRole;
import com.vending.machine.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserNameWithRoles(username)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " is not found"));
    }

    private UserDetails map(User domainUser) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(domainUser.getUsername())
                .password(domainUser.getPassword())
                .passwordEncoder(password -> password)
                .authorities(
                        domainUser.getRoles().stream()
                                .map(UserRole::getRole)
                                .map(RoleType::name)
                                .toArray(String[]::new)
                )
                .build();
    }
}