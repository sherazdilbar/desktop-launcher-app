package com.launcher.service;

import com.launcher.domain.Application;
import com.launcher.repository.ApplicationRepository;
import jakarta.persistence.EntityManager;
import java.io.IOException;
import java.util.List;

public class ApplicationService {
    
    private final EntityManager em;
    private final ApplicationRepository applicationRepository;
    
    public ApplicationService(EntityManager em) {
        this.em = em;
        this.applicationRepository = new ApplicationRepository(em);
    }
    
    public Application createApplication(String id, String name, String iconName, String executablePath) {
        if (applicationRepository.existsById(id)) {
            throw new IllegalArgumentException("Application with ID '" + id + "' already exists");
        }
        
        Application application = new Application(id, name, iconName, executablePath);
        em.getTransaction().begin();
        applicationRepository.save(application);
        em.getTransaction().commit();
        return application;
    }
    
    public Application updateApplication(String id, String name, String iconName, String executablePath) {
        Application application = applicationRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Application with ID '" + id + "' not found"));
        
        application.setName(name);
        application.setIconName(iconName);
        application.setExecutablePath(executablePath);
        
        em.getTransaction().begin();
        applicationRepository.save(application);
        em.getTransaction().commit();
        return application;
    }
    
    public void deleteApplication(String id) {
        Application application = applicationRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Application with ID '" + id + "' not found"));
        
        em.getTransaction().begin();
        applicationRepository.delete(application);
        em.getTransaction().commit();
    }
    
    public Application getApplicationById(String id) {
        return applicationRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Application with ID '" + id + "' not found"));
    }
    
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }
    
    public void launchApplication(String applicationId) throws IOException {
        Application application = getApplicationById(applicationId);
        
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(application.getExecutablePath());
            processBuilder.start();
            System.out.println("Launched application: " + application.getName());
        } catch (IOException e) {
            throw new IOException("Failed to launch application '" + application.getName() + "': " + e.getMessage());
        } catch (SecurityException e) {
            throw new IOException("Permission denied to launch application '" + application.getName() + "'");
        }
    }
}
