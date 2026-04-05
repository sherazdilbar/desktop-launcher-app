package com.launcher.repository;

import com.launcher.domain.Menu;
import com.launcher.domain.MenuItem;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class MenuItemRepository {
    
    private final EntityManager em;
    
    public MenuItemRepository(EntityManager em) {
        this.em = em;
    }
    
    public void save(MenuItem menuItem) {
        if (em.find(MenuItem.class, menuItem.getId()) == null) {
            em.persist(menuItem);
        } else {
            em.merge(menuItem);
        }
    }
    
    public Optional<MenuItem> findById(String id) {
        MenuItem menuItem = em.find(MenuItem.class, id);
        return Optional.ofNullable(menuItem);
    }
    
    public List<MenuItem> findByMenuOrderByDisplayOrder(Menu menu) {
        return em.createQuery(
            "SELECT mi FROM MenuItem mi WHERE mi.menu = :menu ORDER BY mi.displayOrder", 
            MenuItem.class)
            .setParameter("menu", menu)
            .getResultList();
    }
    
    public void delete(MenuItem menuItem) {
        if (em.contains(menuItem)) {
            em.remove(menuItem);
        } else {
            em.remove(em.merge(menuItem));
        }
    }
}
