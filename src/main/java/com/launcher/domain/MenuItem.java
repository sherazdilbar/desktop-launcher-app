package com.launcher.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "menu_items")
public class MenuItem {
    
    @Id
    @Column(length = 100)
    private String id;
    
    @Column(nullable = false, length = 200)
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;
    
    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;
    
    @Column(name = "display_order")
    private Integer displayOrder;
    
    public MenuItem() {
    }
    
    public MenuItem(String id, String name, Integer displayOrder) {
        this.id = id;
        this.name = name;
        this.displayOrder = displayOrder;
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
    
    public Menu getMenu() {
        return menu;
    }
    
    public void setMenu(Menu menu) {
        this.menu = menu;
    }
    
    public Application getApplication() {
        return application;
    }
    
    public void setApplication(Application application) {
        this.application = application;
    }
    
    public Integer getDisplayOrder() {
        return displayOrder;
    }
    
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}
