package com.launcher.repository;

import com.launcher.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    
    private final EntityManager em;
    
    public UserRepository(EntityManager em) {
        this.em = em;
    }
    
    public void save(User user) {
        if (em.find(User.class, user.getId()) == null) {
            em.persist(user);
        } else {
            em.merge(user);
        }
    }
    
    public Optional<User> findById(String id) {
        User user = em.find(User.class, id);
        return Optional.ofNullable(user);
    }
    
    public Optional<User> findByName(String name) {
        try {
            User user = em.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class)
                .setParameter("name", name)
                .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    
    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
    
    public void delete(User user) {
        if (em.contains(user)) {
            em.remove(user);
        } else {
            em.remove(em.merge(user));
        }
    }
    
    public boolean existsById(String id) {
        return em.find(User.class, id) != null;
    }
}
