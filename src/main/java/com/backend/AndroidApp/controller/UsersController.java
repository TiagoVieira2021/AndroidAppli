package com.backend.AndroidApp.controller;

import com.backend.AndroidApp.dao.UsersDao;
import com.backend.AndroidApp.model.Customer;
import com.backend.AndroidApp.model.Users;
import com.backend.AndroidApp.security.JwtUtil;
import com.backend.AndroidApp.security.UserDetailsServiceCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
public class UsersController {

    private UsersDao usersDao;
    private AuthenticationManager authenticationManager;
    private UserDetailsServiceCustom userDetailsServiceCustom;
    private JwtUtil jwtUtil;

    @Autowired
    public UsersController(UsersDao usersDao, AuthenticationManager authenticationManager, UserDetailsServiceCustom userDetailsServiceCustom, JwtUtil jwtUtil) {
        this.usersDao = usersDao;
        this.authenticationManager = authenticationManager;
        this.userDetailsServiceCustom = userDetailsServiceCustom;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/authentification")
    public ResponseEntity<String> authentification(@RequestBody Users user) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getLogin(), user.getLoginPassword()
                    )
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("Mauvais login ou mot de passe");
        }

        UserDetails userDetails = this.userDetailsServiceCustom.loadUserByUsername(user.getLogin());

        return ResponseEntity.ok(jwtUtil.generateToken(userDetails));
    }

    public <T extends Users> Optional<T> getUser(String authorization, Class<T> clazz) {
        String token = authorization.substring(7);

        String login = jwtUtil.getTokenBody(token).getSubject();

        Optional<T> user = (Optional<T>) usersDao.findByLogin(login);

        return user;
    }
}
