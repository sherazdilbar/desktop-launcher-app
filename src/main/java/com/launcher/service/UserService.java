package com.launcher.service;

import com.launcher.domain.Application;
import com.launcher.domain.Avatar;
import com.launcher.domain.User;
import com.launcher.domain.Wallpaper;
import com.launcher.repository.*;
import jakarta.persistence.EntityManager;
import java.util.List;

public class UserService {
    
    private final EntityManager em;
    private final UserRepository userRepository;
    private final WallpaperRepository wallpaperRepository;
    private final AvatarRepository avatarRepository;
    private final ApplicationRepository applicationRepository;
    
    public UserService(EntityManager em) {
        this.em = em;
        this.userRepository = new UserRepository(em);
        this.wallpaperRepository = new WallpaperRepository(em);
        this.avatarRepository = new AvatarRepository(em);
        this.applicationRepository = new ApplicationRepository(em);
    }
    
    public User createUser(String id, String name) {
        if (userRepository.existsById(id)) {
            throw new IllegalArgumentException("User with ID '" + id + "' already exists");
        }
        
        User user = new User(id, name);
        em.getTransaction().begin();
        userRepository.save(user);
        em.getTransaction().commit();
        return user;
    }
    
    public User updateUser(String id, String name) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User with ID '" + id + "' not found"));
        
        user.setName(name);
        em.getTransaction().begin();
        userRepository.save(user);
        em.getTransaction().commit();
        return user;
    }
    
    public void deleteUser(String id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User with ID '" + id + "' not found"));
        
        em.getTransaction().begin();
        userRepository.delete(user);
        em.getTransaction().commit();
    }
    
    public User getUserById(String id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User with ID '" + id + "' not found"));
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public void setWallpaper(String userId, String wallpaperId) {
        User user = getUserById(userId);
        Wallpaper wallpaper = wallpaperRepository.findById(wallpaperId)
            .orElseThrow(() -> new IllegalArgumentException("Wallpaper with ID '" + wallpaperId + "' not found"));
        
        user.setWallpaper(wallpaper);
        em.getTransaction().begin();
        userRepository.save(user);
        em.getTransaction().commit();
    }
    
    public void setAvatar(String userId, String avatarId) {
        User user = getUserById(userId);
        Avatar avatar = avatarRepository.findById(avatarId)
            .orElseThrow(() -> new IllegalArgumentException("Avatar with ID '" + avatarId + "' not found"));
        
        user.setAvatar(avatar);
        em.getTransaction().begin();
        userRepository.save(user);
        em.getTransaction().commit();
    }
    
    public void addFavorite(String userId, String applicationId) {
        User user = getUserById(userId);
        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new IllegalArgumentException("Application with ID '" + applicationId + "' not found"));
        
        em.getTransaction().begin();
        user.addFavorite(application);
        userRepository.save(user);
        em.getTransaction().commit();
    }
    
    public void removeFavorite(String userId, String applicationId) {
        User user = getUserById(userId);
        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new IllegalArgumentException("Application with ID '" + applicationId + "' not found"));
        
        em.getTransaction().begin();
        user.removeFavorite(application);
        userRepository.save(user);
        em.getTransaction().commit();
    }
    
    public List<Application> getFavorites(String userId) {
        User user = getUserById(userId);
        return user.getFavorites();
    }
}
