package com.example.BackEndSem4.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number", length = 10, nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "password", length = 200, nullable = false)
    private String password;

    @Column(name = "fullname", length = 100)
    private String fullName;

    @Column(name = "address", length = 250)
    private String address;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_" + getRole().getName().toUpperCase()));
        return authorityList;
    }

    @Override
    public String getUsername() {
        return phoneNumber;
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
}