package com.launcher.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menus")
public class Menu {
    
    @Id
    @Column(length = 100)
    private String id;
    
    @Column(nullable = false, length = 200)
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "parent_menu_id")
    private Menu parentMenu;
    
    @OneToMany(mappedBy = "parentMenu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> submenus = new ArrayList<>();
    
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItem> menuItems = new ArrayList<>();
    
    public Menu() {
    }
    
    public Menu(String id, String name) {
        this.id = id;
        this.name = name;
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
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Menu getParentMenu() {
        return parentMenu;
    }
    
    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }
    
    public List<Menu> getSubmenus() {
        return submenus;
    }
    
    public void setSubmenus(List<Menu> submenus) {
        this.submenus = submenus;
    }
    
    public List<MenuItem> getMenuItems() {
        return menuItems;
    }
    
    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
}
