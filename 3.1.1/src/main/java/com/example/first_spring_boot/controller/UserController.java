package com.example.first_spring_boot.controller;

import com.example.first_spring_boot.model.Role;
import com.example.first_spring_boot.model.User;
import com.example.first_spring_boot.repository.UserRepository;
import com.example.first_spring_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class UserController {

    private final
    UserService userService;
    private final
    UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/hello")
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("I'm Spring MVC-SECURITY application");
        messages.add("5.2.0 version by sep'19 ");
        model.addAttribute("messages", messages);
        return "hello";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/admin")
    public String UsersList(Model model, Authentication authentication, @ModelAttribute("newUser") User user){
        model.addAttribute("rolesList", userRepository.getRolesList());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("email", userRepository.getUserByName(authentication.getName()).getEmail());
        model.addAttribute("usersRoles", userRepository.getUserByName(authentication.getName()).getRoles());
        return "index";
    }

    @GetMapping( "/user/{id}")
    public String getUser(@PathVariable Long id, Model model, Authentication authentication){
        model.addAttribute("email", userRepository.getUserByName(authentication.getName()).getEmail());
        model.addAttribute("usersRoles", userRepository.getUserByName(authentication.getName()).getRoles());
        model.addAttribute("user", userService.findById(id));
        return "user";
    }

    @GetMapping("/admin/user/{id}")
    public String getUserFromAdmin(@PathVariable Long id, Model model){
        model.addAttribute("user", userService.findById(id));
        return "userfromadmin";
    }

    @GetMapping("/admin/new")
    public String newUser(@ModelAttribute("user") User user){
        return "new";
    }

    @PostMapping("/admin")
    public String create(@ModelAttribute("user") User user){
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("admin/user/{id}/edit")
    public String edit(Model model, @PathVariable Long id){
        model.addAttribute("newUser", userService.findById(id));
        return "edit";
    }

    @PatchMapping ("/admin/edit/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable Long id){
        userService.update(id, user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/user/{id}/delete")
    public String delete(@PathVariable Long id){
        userService.delete(id);
        return "redirect:/admin";
    }
}
