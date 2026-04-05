package com.launcher;

import com.launcher.domain.Application;
import com.launcher.domain.Menu;
import com.launcher.domain.MenuItem;
import com.launcher.domain.User;
import com.launcher.repository.EntityManagerProvider;
import com.launcher.service.*;
import jakarta.persistence.EntityManager;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ConsoleApplication {
    
    private final EntityManager em;
    private final UserService userService;
    private final MenuService menuService;
    private final ApplicationService applicationService;
    private final InitializationService initializationService;
    private final Scanner scanner;
    private User currentUser;
    
    public ConsoleApplication() {
        this.em = EntityManagerProvider.getEntityManager();
        this.userService = new UserService(em);
        this.menuService = new MenuService(em);
        this.applicationService = new ApplicationService(em);
        this.initializationService = new InitializationService(em);
        this.scanner = new Scanner(System.in);
    }
    
    public static void main(String[] args) {
        ConsoleApplication app = new ConsoleApplication();
        app.run();
    }
    
    public void run() {
        try {
            // Initialize data on first run
            initializationService.initializeData();
            
            // Display welcome and select user
            displayWelcome();
            selectUser();
            
            // Main application loop
            boolean running = true;
            while (running) {
                displayMainMenu();
                String command = scanner.nextLine().trim().toLowerCase();
                
                switch (command) {
                    case "1":
                    case "menu":
                        navigateMenus();
                        break;
                    case "2":
                    case "favorites":
                    case "fav":
                        displayFavorites();
                        break;
                    case "3":
                    case "profile":
                        displayProfile();
                        break;
                    case "4":
                    case "switch":
                        selectUser();
                        break;
                    case "5":
                    case "exit":
                    case "quit":
                        running = false;
                        System.out.println("\nGoodbye!");
                        break;
                    case "help":
                        displayHelp();
                        break;
                    default:
                        System.out.println("Invalid option. Type 'help' for available commands.");
                }
            }
        } finally {
            scanner.close();
            EntityManagerProvider.close();
        }
    }
    
    private void displayWelcome() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║   DESKTOP LAUNCHER APPLICATION             ║");
        System.out.println("║   Smart Device Management System           ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
    }
    
    private void selectUser() {
        System.out.println("\n=== SELECT USER ===");
        List<User> users = userService.getAllUsers();
        
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            System.out.printf("%d. %s (ID: %s)\n", i + 1, user.getName(), user.getId());
        }
        
        System.out.print("\nEnter user number: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice > 0 && choice <= users.size()) {
                currentUser = users.get(choice - 1);
                System.out.println("\nWelcome, " + currentUser.getName() + "!");
            } else {
                System.out.println("Invalid selection. Defaulting to first user.");
                currentUser = users.get(0);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Defaulting to first user.");
            currentUser = users.get(0);
        }
    }
    
    private void displayMainMenu() {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("User: " + currentUser.getName());
        if (currentUser.getWallpaper() != null) {
            System.out.println("Wallpaper: " + currentUser.getWallpaper().getName());
        }
        if (currentUser.getAvatar() != null) {
            System.out.println("Avatar: " + currentUser.getAvatar().getName());
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("\n1. Browse Menus");
        System.out.println("2. View Favorites");
        System.out.println("3. View Profile");
        System.out.println("4. Switch User");
        System.out.println("5. Exit");
        System.out.println("\nType 'help' for more commands");
        System.out.print("\nChoice: ");
    }
    
    private void navigateMenus() {
        List<Menu> rootMenus = menuService.getRootMenusForUser(currentUser.getId());
        
        if (rootMenus.isEmpty()) {
            System.out.println("\nNo menus available for this user.");
            return;
        }
        
        System.out.println("\n=== YOUR MENUS ===");
        for (int i = 0; i < rootMenus.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, rootMenus.get(i).getName());
        }
        System.out.print("\nSelect menu (or 'back'): ");
        
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("back")) {
            return;
        }
        
        try {
            int choice = Integer.parseInt(input);
            if (choice > 0 && choice <= rootMenus.size()) {
                navigateMenu(rootMenus.get(choice - 1));
            } else {
                System.out.println("Invalid selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }
    
    private void navigateMenu(Menu menu) {
        while (true) {
            System.out.println("\n=== " + menu.getName().toUpperCase() + " ===");
            
            // Display submenus
            List<Menu> submenus = menuService.getSubmenus(menu.getId());
            int index = 1;
            
            if (!submenus.isEmpty()) {
                System.out.println("\nSubmenus:");
                for (Menu submenu : submenus) {
                    System.out.printf("%d. [MENU] %s\n", index++, submenu.getName());
                }
            }
            
            // Display menu items
            List<MenuItem> menuItems = menuService.getMenuItems(menu.getId());
            if (!menuItems.isEmpty()) {
                System.out.println("\nApplications:");
                for (MenuItem item : menuItems) {
                    System.out.printf("%d. %s\n", index++, item.getName());
                }
            }
            
            if (submenus.isEmpty() && menuItems.isEmpty()) {
                System.out.println("\nThis menu is empty.");
            }
            
            System.out.print("\nSelect option (or 'back'): ");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("back")) {
                return;
            }
            
            try {
                int choice = Integer.parseInt(input);
                if (choice > 0 && choice <= submenus.size()) {
                    // Navigate to submenu
                    navigateMenu(submenus.get(choice - 1));
                } else if (choice > submenus.size() && choice < index) {
                    // Launch application
                    MenuItem selectedItem = menuItems.get(choice - submenus.size() - 1);
                    launchApplication(selectedItem.getApplication());
                } else {
                    System.out.println("Invalid selection.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }
    }
    
    private void launchApplication(Application app) {
        System.out.println("\n→ Launching: " + app.getName());
        System.out.println("  Executable: " + app.getExecutablePath());
        System.out.println("  Icon: " + app.getIconName());
        
        try {
            applicationService.launchApplication(app.getId());
            System.out.println("✓ Application launched successfully!");
        } catch (IOException e) {
            System.out.println("✗ Failed to launch application: " + e.getMessage());
            System.out.println("  (This is expected in a demo environment)");
        }
        
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    private void displayFavorites() {
        System.out.println("\n=== YOUR FAVORITES ===");
        List<Application> favorites = userService.getFavorites(currentUser.getId());
        
        if (favorites.isEmpty()) {
            System.out.println("\nYou have no favorite applications yet.");
            System.out.print("\nPress Enter to continue...");
            scanner.nextLine();
            return;
        }
        
        for (int i = 0; i < favorites.size(); i++) {
            Application app = favorites.get(i);
            System.out.printf("%d. %s (%s)\n", i + 1, app.getName(), app.getIconName());
        }
        
        System.out.print("\nLaunch favorite (number) or 'back': ");
        String input = scanner.nextLine().trim();
        
        if (input.equalsIgnoreCase("back")) {
            return;
        }
        
        try {
            int choice = Integer.parseInt(input);
            if (choice > 0 && choice <= favorites.size()) {
                launchApplication(favorites.get(choice - 1));
            } else {
                System.out.println("Invalid selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }
    
    private void displayProfile() {
        System.out.println("\n=== USER PROFILE ===");
        System.out.println("Name: " + currentUser.getName());
        System.out.println("ID: " + currentUser.getId());
        
        if (currentUser.getWallpaper() != null) {
            System.out.println("Wallpaper: " + currentUser.getWallpaper().getName());
            System.out.println("  Path: " + currentUser.getWallpaper().getImagePath());
        }
        
        if (currentUser.getAvatar() != null) {
            System.out.println("Avatar: " + currentUser.getAvatar().getName());
            System.out.println("  Path: " + currentUser.getAvatar().getImagePath());
        }
        
        System.out.println("Favorites: " + currentUser.getFavorites().size() + " applications");
        System.out.println("Menus: " + menuService.getRootMenusForUser(currentUser.getId()).size());
        
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    private void displayHelp() {
        System.out.println("\n=== HELP ===");
        System.out.println("Available commands:");
        System.out.println("  1, menu      - Browse your menus");
        System.out.println("  2, favorites - View and launch favorite apps");
        System.out.println("  3, profile   - View your profile");
        System.out.println("  4, switch    - Switch to another user");
        System.out.println("  5, exit      - Exit the application");
        System.out.println("  help         - Show this help message");
        System.out.println("  back         - Go back to previous screen");
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
