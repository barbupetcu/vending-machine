package com.vending.machine.domain.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USER")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    private String username;
    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> roles;
    private BigDecimal deposit;

    public void addRole(UserRole userRole) {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        roles.add(userRole);
        userRole.setUser(this);
    }

    public void removeRole(UserRole userRole) {
        if (roles != null) {
            roles.remove(userRole);
            userRole.setUser(null);
        }
    }
}
