package com.launcher.service;

import com.launcher.domain.*;
import jakarta.persistence.EntityManager;

public class InitializationService {
    
    private final EntityManager em;
    private final UserService userService;
    private final ApplicationService applicationService;
    private final MenuService menuService;
    private final AssetService assetService;
    
    public InitializationService(EntityManager em) {
        this.em = em;
        this.userService = new UserService(em);
        this.applicationService = new ApplicationService(em);
        this.menuService = new MenuService(em);
        this.assetService = new AssetService(em);
    }
    
    public void initializeData() {
        // Check if data already exists
        if (!userService.getAllUsers().isEmpty()) {
            System.out.println("Data already initialized. Skipping initialization.");
            return;
        }
        
        System.out.println("Initializing sample data...");
        
        createSampleAssets();
        createSampleUsers();
        createSampleApplications();
        createSampleMenus();
        establishSampleFavorites();
        
        System.out.println("Sample data initialization complete!");
    }
    
    private void createSampleAssets() {
        // Create wallpapers
        assetService.createWallpaper("wallpaper-1", "Mountain Landscape", "/assets/wallpapers/mountain.jpg");
        assetService.createWallpaper("wallpaper-2", "Ocean Sunset", "/assets/wallpapers/ocean.jpg");
        assetService.createWallpaper("wallpaper-3", "City Lights", "/assets/wallpapers/city.jpg");
        
        // Create avatars
        assetService.createAvatar("avatar-1", "Professional", "/assets/avatars/professional.png");
        assetService.createAvatar("avatar-2", "Casual", "/assets/avatars/casual.png");
        assetService.createAvatar("avatar-3", "Fun", "/assets/avatars/fun.png");
    }
    
    private void createSampleUsers() {
        // Create your user
        User sheraz = userService.createUser("sheraz-dilbar", "Sheraz Dilbar");
        userService.setWallpaper("sheraz-dilbar", "wallpaper-1");
        userService.setAvatar("sheraz-dilbar", "avatar-1");
        
        // Create additional sample users
        User parent1 = userService.createUser("parent-1", "John Smith");
        userService.setWallpaper("parent-1", "wallpaper-2");
        userService.setAvatar("parent-1", "avatar-2");
        
        User parent2 = userService.createUser("parent-2", "Jane Smith");
        userService.setWallpaper("parent-2", "wallpaper-3");
        userService.setAvatar("parent-2", "avatar-3");
        
        User child1 = userService.createUser("child-1", "Tommy Smith");
        userService.setWallpaper("child-1", "wallpaper-1");
        userService.setAvatar("child-1", "avatar-3");
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
    
    private void createSampleMenus() {
        // Create menus for Sheraz Dilbar
        Menu sherazMain = menuService.createMenu("menu-sheraz-main", "Main Menu", "sheraz-dilbar");
        menuService.addMenuItem("menu-sheraz-main", "app-browser", "Web Browser", 1);
        menuService.addMenuItem("menu-sheraz-main", "app-terminal", "Terminal", 2);
        menuService.addMenuItem("menu-sheraz-main", "app-notepad", "Notepad", 3);
        
        Menu sherazGames = menuService.createSubmenu("menu-sheraz-games", "Games", "menu-sheraz-main");
        menuService.addMenuItem("menu-sheraz-games", "app-minesweeper", "Minesweeper", 1);
        
        Menu sherazTools = menuService.createSubmenu("menu-sheraz-tools", "Tools", "menu-sheraz-main");
        menuService.addMenuItem("menu-sheraz-tools", "app-calculator", "Calculator", 1);
        menuService.addMenuItem("menu-sheraz-tools", "app-paint", "Paint", 2);
        
        // Create menus for Parent 1 (Father - GPS focused)
        Menu parent1Main = menuService.createMenu("menu-parent1-main", "Main Menu", "parent-1");
        menuService.addMenuItem("menu-parent1-main", "app-openmap", "GPS Navigation", 1);
        menuService.addMenuItem("menu-parent1-main", "app-browser", "Web Browser", 2);
        menuService.addMenuItem("menu-parent1-main", "app-directory", "File Manager", 3);
        
        // Create menus for Parent 2 (Mother - Recipes focused)
        Menu parent2Main = menuService.createMenu("menu-parent2-main", "Main Menu", "parent-2");
        menuService.addMenuItem("menu-parent2-main", "app-browser", "Recipe Browser", 1);
        menuService.addMenuItem("menu-parent2-main", "app-notepad", "Recipe Notes", 2);
        menuService.addMenuItem("menu-parent2-main", "app-calculator", "Calculator", 3);
        
        // Create menus for Child (Games focused)
        Menu child1Main = menuService.createMenu("menu-child1-main", "Main Menu", "child-1");
        menuService.addMenuItem("menu-child1-main", "app-minesweeper", "Minesweeper", 1);
        menuService.addMenuItem("menu-child1-main", "app-paint", "Paint", 2);
        
        Menu child1Games = menuService.createSubmenu("menu-child1-games", "More Games", "menu-child1-main");
        menuService.addMenuItem("menu-child1-games", "app-minesweeper", "Minesweeper", 1);
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
