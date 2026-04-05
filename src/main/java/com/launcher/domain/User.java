package com.launcher.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @Column(length = 100)
    private String id;
    
    @Column(nullable = false, length = 200)
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "wallpaper_id")
    private Wallpaper wallpaper;
    
    @ManyToOne
    @JoinColumn(name = "avatar_id")
    private Avatar avatar;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menus = new ArrayList<>();
    
    @ManyToMany
    @JoinTable(
        name = "user_favorites",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "application_id")
    )
    private List<Application> favorites = new ArrayList<>();
    
    public User() {
    }
    
    public User(String id, String name) {
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
    
    public Wallpaper getWallpaper() {
        return wallpaper;
    }
    
    public void setWallpaper(Wallpaper wallpaper) {
        this.wallpaper = wallpaper;
    }
    
    public Avatar getAvatar() {
        return avatar;
    }
    
    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
    
    public List<Menu> getMenus() {
        return menus;
    }
    
    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
    
    public List<Application> getFavorites() {
        return favorites;
    }
    
    public void setFavorites(List<Application> favorites) {
        this.favorites = favorites;
    }
    
    public void addFavorite(Application application) {
        if (!favorites.contains(application)) {
            favorites.add(application);
            application.getFavoritedByUsers().add(this);
        }
    }
    
    public void removeFavorite(Application application) {
        favorites.remove(application);
        application.getFavoritedByUsers().remove(this);
    }
}
