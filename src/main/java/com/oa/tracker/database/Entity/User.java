package com.oa.tracker.database.Entity;

import com.oa.tracker.database.categories.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @Setter
    private String email;

    @Getter
    @Column
    @Setter
    String firstName;

    @Getter
    @Column
    @Setter
    String lastName;

    @Column
    @Setter
    private String passwordHash;

    @Getter
    @Column
    @Setter
    private Role role;

    @Column(nullable = false, updatable = false)
    @Getter
    private LocalDateTime createdAt = LocalDateTime.now();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority(role.toString()));
    }

    public User(String email, String firstName, String lastName, String password, Role role) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwordHash = password;
        this.role = role;
    };

    @Override
    public @Nullable String getPassword() {
        return this.passwordHash;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

}
