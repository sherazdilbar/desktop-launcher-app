package com.launcher.service;

import com.launcher.domain.Avatar;
import com.launcher.domain.Wallpaper;
import com.launcher.repository.AvatarRepository;
import com.launcher.repository.WallpaperRepository;
import jakarta.persistence.EntityManager;
import java.util.List;

public class AssetService {
    
    private final EntityManager em;
    private final WallpaperRepository wallpaperRepository;
    private final AvatarRepository avatarRepository;
    
    public AssetService(EntityManager em) {
        this.em = em;
        this.wallpaperRepository = new WallpaperRepository(em);
        this.avatarRepository = new AvatarRepository(em);
    }
    
    public Wallpaper createWallpaper(String id, String name, String imagePath) {
        Wallpaper wallpaper = new Wallpaper(id, name, imagePath);
        em.getTransaction().begin();
        wallpaperRepository.save(wallpaper);
        em.getTransaction().commit();
        return wallpaper;
    }
    
    public void deleteWallpaper(String id) {
        Wallpaper wallpaper = wallpaperRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Wallpaper with ID '" + id + "' not found"));
        
        if (!wallpaper.getUsers().isEmpty()) {
            throw new IllegalStateException("Cannot delete wallpaper '" + id + "' - currently assigned to " + 
                wallpaper.getUsers().size() + " user(s)");
        }
        
        em.getTransaction().begin();
        wallpaperRepository.delete(wallpaper);
        em.getTransaction().commit();
    }
    
    public List<Wallpaper> getAllWallpapers() {
        return wallpaperRepository.findAll();
    }
    
    public Avatar createAvatar(String id, String name, String imagePath) {
        Avatar avatar = new Avatar(id, name, imagePath);
        em.getTransaction().begin();
        avatarRepository.save(avatar);
        em.getTransaction().commit();
        return avatar;
    }
    
    public void deleteAvatar(String id) {
        Avatar avatar = avatarRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Avatar with ID '" + id + "' not found"));
        
        if (!avatar.getUsers().isEmpty()) {
            throw new IllegalStateException("Cannot delete avatar '" + id + "' - currently assigned to " + 
                avatar.getUsers().size() + " user(s)");
        }
        
        em.getTransaction().begin();
        avatarRepository.delete(avatar);
        em.getTransaction().commit();
    }
    
    public List<Avatar> getAllAvatars() {
        return avatarRepository.findAll();
    }
}
