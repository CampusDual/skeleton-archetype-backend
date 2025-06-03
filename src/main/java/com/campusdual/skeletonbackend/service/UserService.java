package com.campusdual.skeletonbackend.service;

import com.campusdual.skeletonbackend.model.Role;
import com.campusdual.skeletonbackend.model.User;
import com.campusdual.skeletonbackend.model.UserRole;
import com.campusdual.skeletonbackend.model.dao.RoleDao;
import com.campusdual.skeletonbackend.model.dao.UserDao;
import com.campusdual.skeletonbackend.model.dao.UserRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Lazy
public class UserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userDao.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        List<UserRole> userRoles = this.userRoleDao.findByUser(user);

        List<GrantedAuthority> authorities = userRoles.stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().getRoleName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    public boolean existsByUsername(String username) {
        User user = this.userDao.findByLogin(username);
        return user != null;
    }

    public void registerNewUser(String username, String password) {
        User user = new User();
        user.setLogin(username);
        user.setName(username);
        user.setPassword(this.passwordEncoder().encode(password));
        User savedUser = this.userDao.saveAndFlush(user);

        Role role = this.roleDao.findByRoleName("ROLE_USER");
        if (role != null) {
            UserRole userRole = new UserRole();
            userRole.setUser(savedUser);
            userRole.setRole(role);
            this.userRoleDao.saveAndFlush(userRole);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
