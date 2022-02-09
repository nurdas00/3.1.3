package com.example.first_spring_boot.repository;

import com.example.first_spring_boot.model.Role;
import com.example.first_spring_boot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select role from Role role where role.id = ?1")
    Role getRole(Long id);

    @Query("select role from Role role")
    List<Role> getRolesList();

    User getUserByName(String name);

    @Query("select user from User user where user.email = ?1")
    User getUserByEmail(String email);
}
