package com.launcher.service;

import com.launcher.domain.Application;
import com.launcher.domain.Avatar;
import com.launcher.domain.User;
import com.launcher.domain.Wallpaper;
import com.launcher.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    private final WallpaperRepository wallpaperRepository;
    private final AvatarRepository avatarRepository;
    private final ApplicationRepository applicationRepository;
    
    public UserService(UserRepository userRepository,
                      WallpaperRepository wallpaperRepository,
                      AvatarRepository avatarRepository,
                      ApplicationRepository applicationRepository) {
        this.userRepository = userRepository;
        this.wallpaperRepository = wallpaperRepository;
        this.avatarRepository = avatarRepository;
        this.applicationRepository = applicationRepository;
    }
    
    public User createUser(String id, String name) {
        if (userRepository.existsById(id)) {
            throw new IllegalArgumentException("User with ID '" + id + "' already exists");
        }
        
        User user = new User(id, name);
        return userRepository.save(user);
    }
    
    public User updateUser(String id, String name) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User with ID '" + id + "' not found"));
        
        user.setName(name);
        return userRepository.save(user);
    }
    
    public void deleteUser(String id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User with ID '" + id + "' not found"));
        
        userRepository.delete(user);
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
        userRepository.save(user);
    }
    
    public void setAvatar(String userId, String avatarId) {
        User user = getUserById(userId);
        Avatar avatar = avatarRepository.findById(avatarId)
            .orElseThrow(() -> new IllegalArgumentException("Avatar with ID '" + avatarId + "' not found"));
        
        user.setAvatar(avatar);
        userRepository.save(user);
    }
    
    public void addFavorite(String userId, String applicationId) {
        User user = getUserById(userId);
        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new IllegalArgumentException("Application with ID '" + applicationId + "' not found"));
        
        user.addFavorite(application);
        userRepository.save(user);
    }
    
    public void removeFavorite(String userId, String applicationId) {
        User user = getUserById(userId);
        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new IllegalArgumentException("Application with ID '" + applicationId + "' not found"));
        
        user.removeFavorite(application);
        userRepository.save(user);
    }
    
    public List<Application> getFavorites(String userId) {
        User user = getUserById(userId);
        return user.getFavorites();
    }
}
