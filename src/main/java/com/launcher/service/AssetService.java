package com.launcher.service;

import com.launcher.domain.Avatar;
import com.launcher.domain.Wallpaper;
import com.launcher.repository.AvatarRepository;
import com.launcher.repository.WallpaperRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class AssetService {
    
    private final WallpaperRepository wallpaperRepository;
    private final AvatarRepository avatarRepository;
    
    public AssetService(WallpaperRepository wallpaperRepository, AvatarRepository avatarRepository) {
        this.wallpaperRepository = wallpaperRepository;
        this.avatarRepository = avatarRepository;
    }
    
    public Wallpaper createWallpaper(String id, String name, String imagePath) {
        Wallpaper wallpaper = new Wallpaper(id, name, imagePath);
        return wallpaperRepository.save(wallpaper);
    }
    
    public void deleteWallpaper(String id) {
        Wallpaper wallpaper = wallpaperRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Wallpaper with ID '" + id + "' not found"));
        
        if (!wallpaper.getUsers().isEmpty()) {
            throw new IllegalStateException("Cannot delete wallpaper '" + id + "' - currently assigned to " + 
                wallpaper.getUsers().size() + " user(s)");
        }
        
        wallpaperRepository.delete(wallpaper);
    }
    
    public List<Wallpaper> getAllWallpapers() {
        return wallpaperRepository.findAll();
    }
    
    public Avatar createAvatar(String id, String name, String imagePath) {
        Avatar avatar = new Avatar(id, name, imagePath);
        return avatarRepository.save(avatar);
    }
    
    public void deleteAvatar(String id) {
        Avatar avatar = avatarRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Avatar with ID '" + id + "' not found"));
        
        if (!avatar.getUsers().isEmpty()) {
            throw new IllegalStateException("Cannot delete avatar '" + id + "' - currently assigned to " + 
                avatar.getUsers().size() + " user(s)");
        }
        
        avatarRepository.delete(avatar);
    }
    
    public List<Avatar> getAllAvatars() {
        return avatarRepository.findAll();
    }
}
