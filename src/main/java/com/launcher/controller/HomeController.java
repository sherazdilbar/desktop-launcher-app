package com.launcher.controller;

import com.launcher.service.InitializationService;
import com.launcher.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {
    
    private final UserService userService;
    private final InitializationService initializationService;
    
    public HomeController(UserService userService, InitializationService initializationService) {
        this.userService = userService;
        this.initializationService = initializationService;
    }
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }
    
    @PostMapping("/simulate")
    public String simulate(RedirectAttributes redirectAttributes) {
        try {
            initializationService.initializeData();
            redirectAttributes.addFlashAttribute("message", "Test data populated successfully!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Data already exists or error: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "info");
        }
        return "redirect:/";
    }
}
