package com.vending.machine.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "USER_ROLE")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class UserRole extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Enumerated(EnumType.STRING)
    private RoleType role;

    public static UserRole fromRole(RoleType role) {
        return UserRole.builder().role(role).build();
    }
}
