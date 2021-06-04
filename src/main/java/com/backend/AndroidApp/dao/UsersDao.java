package com.backend.AndroidApp.dao;

import com.backend.AndroidApp.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersDao extends JpaRepository<Users, Integer> {
    Optional<Users> findByLogin(String login);
}
