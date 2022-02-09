package com.example.first_spring_boot.service;

import com.example.first_spring_boot.model.Role;
import com.example.first_spring_boot.model.User;
import com.example.first_spring_boot.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id) {
        return userRepository.getById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void save(User user) {
        Set<Role> roles = user.getRoles();
        user.setRoles(new HashSet<>());
        userRepository.save(user);
        if(roles.stream().findFirst().get().toString().contains("USER")){
            user.addRole(userRepository.getRole(2L));
        } else {
            user.addRole(userRepository.getRole(1L));
        }
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public void update(Long id, User user) {
        User updatedUser = userRepository.getById(id);
        updatedUser.setName(user.getName());
        updatedUser.setlastname(user.getlastname());
        updatedUser.setAge(user.getAge());
        updatedUser.setEmail(user.getEmail());
        Set<Role> roles = user.getRoles();
        updatedUser.setRoles(new HashSet<>());
        if(roles.stream().findFirst().get().toString().contains("USER")){
            updatedUser.addRole(userRepository.getRole(2L));
        } else {
            updatedUser.addRole(userRepository.getRole(1L));
        }
        updatedUser.setPassword(user.getPassword());
        userRepository.save(updatedUser);
    }

    /*public List<Role> getRoles(){
        return userRepository.getRolesList();
    }*/
}
