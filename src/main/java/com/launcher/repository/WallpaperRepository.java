package com.launcher.repository;

import com.launcher.domain.Wallpaper;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class WallpaperRepository {
    
    private final EntityManager em;
    
    public WallpaperRepository(EntityManager em) {
        this.em = em;
    }
    
    public void save(Wallpaper wallpaper) {
        if (em.find(Wallpaper.class, wallpaper.getId()) == null) {
            em.persist(wallpaper);
        } else {
            em.merge(wallpaper);
        }
    }
    
    public Optional<Wallpaper> findById(String id) {
        Wallpaper wallpaper = em.find(Wallpaper.class, id);
        return Optional.ofNullable(wallpaper);
    }
    
    public List<Wallpaper> findAll() {
        return em.createQuery("SELECT w FROM Wallpaper w", Wallpaper.class).getResultList();
    }
    
    public void delete(Wallpaper wallpaper) {
        if (em.contains(wallpaper)) {
            em.remove(wallpaper);
        } else {
            em.remove(em.merge(wallpaper));
        }
    }
}
