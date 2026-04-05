package com.launcher.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InitializationService {
    
    private final UserService userService;
    private final ApplicationService applicationService;
    
    public InitializationService(UserService userService, ApplicationService applicationService) {
        this.userService = userService;
        this.applicationService = applicationService;
    }
    
    public void initializeData() {
        // Check if data already exists
        if (!userService.getAllUsers().isEmpty()) {
            System.out.println("Data already initialized. Skipping initialization.");
            return;
        }
        
        System.out.println("Initializing sample data...");
        
        createSampleUsers();
        createSampleApplications();
        establishSampleFavorites();
        
        System.out.println("Sample data initialization complete!");
    }
    
    private void createSampleUsers() {
        // Create your user
        userService.createUser("sheraz-dilbar", "Sheraz Dilbar");
        
        // Create additional sample users
        userService.createUser("parent-1", "John Smith");
        userService.createUser("parent-2", "Jane Smith");
        userService.createUser("child-1", "Tommy Smith");
    }
    
    private void createSampleApplications() {
        // Create sample applications as specified in requirements
        applicationService.createApplication("app-minesweeper", "Minesweeper", "mine-icon", "minesweeper");
        applicationService.createApplication("app-openmap", "OpenMap", "map-icon", "openmap");
        applicationService.createApplication("app-paint", "Paint", "paint-icon", "mspaint");
        applicationService.createApplication("app-directory", "Directory", "folder-icon", "explorer");
        
        // Additional useful applications
        applicationService.createApplication("app-browser", "Web Browser", "browser-icon", "chrome");
        applicationService.createApplication("app-notepad", "Notepad", "notepad-icon", "notepad");
        applicationService.createApplication("app-calculator", "Calculator", "calc-icon", "calc");
        applicationService.createApplication("app-terminal", "Terminal", "terminal-icon", "cmd");
    }
    
    private void establishSampleFavorites() {
        // Sheraz Dilbar's favorites
        userService.addFavorite("sheraz-dilbar", "app-browser");
        userService.addFavorite("sheraz-dilbar", "app-terminal");
        userService.addFavorite("sheraz-dilbar", "app-notepad");
        
        // Parent 1's favorites
        userService.addFavorite("parent-1", "app-openmap");
        userService.addFavorite("parent-1", "app-browser");
        
        // Parent 2's favorites
        userService.addFavorite("parent-2", "app-browser");
        userService.addFavorite("parent-2", "app-notepad");
        
        // Child's favorites
        userService.addFavorite("child-1", "app-minesweeper");
        userService.addFavorite("child-1", "app-paint");
    }
}
