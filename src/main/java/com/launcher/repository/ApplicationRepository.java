package com.launcher.repository;

import com.launcher.domain.Application;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public class ApplicationRepository {
    
    private final EntityManager em;
    
    public ApplicationRepository(EntityManager em) {
        this.em = em;
    }
    
    public void save(Application application) {
        if (em.find(Application.class, application.getId()) == null) {
            em.persist(application);
        } else {
            em.merge(application);
        }
    }
    
    public Optional<Application> findById(String id) {
        Application application = em.find(Application.class, id);
        return Optional.ofNullable(application);
    }
    
    public Optional<Application> findByName(String name) {
        try {
            Application application = em.createQuery(
                "SELECT a FROM Application a WHERE a.name = :name", Application.class)
                .setParameter("name", name)
                .getSingleResult();
            return Optional.of(application);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    
    public List<Application> findAll() {
        return em.createQuery("SELECT a FROM Application a", Application.class).getResultList();
    }
    
    public void delete(Application application) {
        if (em.contains(application)) {
            em.remove(application);
        } else {
            em.remove(em.merge(application));
        }
    }
    
    public boolean existsById(String id) {
        return em.find(Application.class, id) != null;
    }
}
