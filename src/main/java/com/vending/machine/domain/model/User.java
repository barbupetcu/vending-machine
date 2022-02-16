package com.vending.machine.domain.model;

import com.vending.machine.application.model.Coin;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
    private Integer deposit;

    public User addDeposit(Coin coin) {
        this.deposit += coin.getValue();
        return this;
    }

    public User subtract(Integer amount) {
        this.deposit -= amount;
        return this;
    }

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
