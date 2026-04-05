package com.launcher.repository;

import com.launcher.domain.Avatar;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class AvatarRepository {
    
    private final EntityManager em;
    
    public AvatarRepository(EntityManager em) {
        this.em = em;
    }
    
    public void save(Avatar avatar) {
        if (em.find(Avatar.class, avatar.getId()) == null) {
            em.persist(avatar);
        } else {
            em.merge(avatar);
        }
    }
    
    public Optional<Avatar> findById(String id) {
        Avatar avatar = em.find(Avatar.class, id);
        return Optional.ofNullable(avatar);
    }
    
    public List<Avatar> findAll() {
        return em.createQuery("SELECT a FROM Avatar a", Avatar.class).getResultList();
    }
    
    public void delete(Avatar avatar) {
        if (em.contains(avatar)) {
            em.remove(avatar);
        } else {
            em.remove(em.merge(avatar));
        }
    }
}
