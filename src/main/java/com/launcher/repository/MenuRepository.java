package com.launcher.repository;

import com.launcher.domain.Menu;
import com.launcher.domain.User;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class MenuRepository {
    
    private final EntityManager em;
    
    public MenuRepository(EntityManager em) {
        this.em = em;
    }
    
    public void save(Menu menu) {
        if (em.find(Menu.class, menu.getId()) == null) {
            em.persist(menu);
        } else {
            em.merge(menu);
        }
    }
    
    public Optional<Menu> findById(String id) {
        Menu menu = em.find(Menu.class, id);
        return Optional.ofNullable(menu);
    }
    
    public List<Menu> findByUserAndParentMenuIsNull(User user) {
        return em.createQuery(
            "SELECT m FROM Menu m WHERE m.user = :user AND m.parentMenu IS NULL", Menu.class)
            .setParameter("user", user)
            .getResultList();
    }
    
    public List<Menu> findByParentMenu(Menu parentMenu) {
        return em.createQuery(
            "SELECT m FROM Menu m WHERE m.parentMenu = :parent", Menu.class)
            .setParameter("parent", parentMenu)
            .getResultList();
    }
    
    public List<Menu> findAll() {
        return em.createQuery("SELECT m FROM Menu m", Menu.class).getResultList();
    }
    
    public void delete(Menu menu) {
        if (em.contains(menu)) {
            em.remove(menu);
        } else {
            em.remove(em.merge(menu));
        }
    }
}
