package com.launcher.controller;

import com.launcher.domain.User;
import com.launcher.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/list";
    }
    
    @GetMapping("/new")
    public String newUserForm(Model model) {
        model.addAttribute("user", new User());
        return "users/form";
    }
    
    @PostMapping
    public String createUser(@RequestParam String id, 
                            @RequestParam String name,
                            RedirectAttributes redirectAttributes) {
        try {
            userService.createUser(id, name);
            redirectAttributes.addFlashAttribute("message", "User created successfully!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "danger");
        }
        return "redirect:/users";
    }
    
    @GetMapping("/{id}")
    public String viewUser(@PathVariable String id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("favorites", userService.getFavorites(id));
        return "users/view";
    }
    
    @GetMapping("/{id}/edit")
    public String editUserForm(@PathVariable String id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "users/edit";
    }
    
    @PostMapping("/{id}")
    public String updateUser(@PathVariable String id,
                            @RequestParam String name,
                            RedirectAttributes redirectAttributes) {
        try {
            userService.updateUser(id, name);
            redirectAttributes.addFlashAttribute("message", "User updated successfully!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "danger");
        }
        return "redirect:/users";
    }
    
    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("message", "User deleted successfully!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "danger");
        }
        return "redirect:/users";
    }
}
