package com.project.resiRed.entity;

import com.project.resiRed.dto.UserDto;
import com.project.resiRed.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 30)
    private String name;

    @Column(length = 30)
    private String lastname;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToMany(mappedBy = "users")
    private Set<Assembly> assemblies;

    @ManyToMany(mappedBy = "users")
    private Set<Question> questions;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((role.name())));
    }

    @Override
    public String getUsername() {
        return email; // email
    }

    public String getFirstName(){
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserDto getDto(){
        UserDto newUserDto=new UserDto();
        newUserDto.setFirstname(getFirstName());
        newUserDto.setLastname(getLastname());
        newUserDto.setAddress(getAddress());
        newUserDto.setEmail(getEmail());
        return newUserDto;
    }
}
