package com.backend.AndroidApp.security;

import com.backend.AndroidApp.dao.UsersDao;
import com.backend.AndroidApp.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceCustom implements UserDetailsService {

    Users user;

    @Autowired
    UsersDao usersDao;

    @Override
    public UserDetails loadUserByUsername(String loginSaisi) throws UsernameNotFoundException {

        user = usersDao
                .findByLogin(loginSaisi)
                .orElseThrow(() -> new UsernameNotFoundException(loginSaisi + " inconnu"));

        return new UserDetailsCustom(user);
    }

    public Users getCustomUser() {
        return user;
    }
}
