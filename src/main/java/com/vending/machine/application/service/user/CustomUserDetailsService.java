package com.vending.machine.application.service.user;

import com.vending.machine.domain.model.RoleType;
import com.vending.machine.domain.model.User;
import com.vending.machine.domain.model.UserRole;
import com.vending.machine.domain.repository.UserRepository;
import com.vending.machine.application.model.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        List<GrantedAuthority> authorities = domainUser.getRoles().stream()
                .map(UserRole::getRole)
                .map(RoleType::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return CustomUserDetail.builder()
                .userId(domainUser.getId())
                .username(domainUser.getUsername())
                .password(domainUser.getPassword())
                .authorities(authorities)
                .build();
    }
}