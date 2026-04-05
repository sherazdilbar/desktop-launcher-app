package com.launcher.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "applications")
public class Application {
    
    @Id
    @Column(length = 100)
    private String id;
    
    @Column(nullable = false, length = 200)
    private String name;
    
    @Column(nullable = false, length = 200)
    private String iconName;
    
    @Column(nullable = false, length = 500)
    private String executablePath;
    
    @ManyToMany(mappedBy = "favorites")
    private List<User> favoritedByUsers = new ArrayList<>();
    
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItem> menuItems = new ArrayList<>();
    
    public Application() {
    }
    
    public Application(String id, String name, String iconName, String executablePath) {
        this.id = id;
        this.name = name;
        this.iconName = iconName;
        this.executablePath = executablePath;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getIconName() {
        return iconName;
    }
    
    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
    
    public String getExecutablePath() {
        return executablePath;
    }
    
    public void setExecutablePath(String executablePath) {
        this.executablePath = executablePath;
    }
    
    public List<User> getFavoritedByUsers() {
        return favoritedByUsers;
    }
    
    public void setFavoritedByUsers(List<User> favoritedByUsers) {
        this.favoritedByUsers = favoritedByUsers;
    }
    
    public List<MenuItem> getMenuItems() {
        return menuItems;
    }
    
    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
}
