package com.launcher.service;

import com.launcher.domain.Application;
import com.launcher.repository.ApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ApplicationService {
    
    private final ApplicationRepository applicationRepository;
    
    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }
    
    public Application createApplication(String id, String name, String iconName, String executablePath) {
        if (applicationRepository.existsById(id)) {
            throw new IllegalArgumentException("Application with ID '" + id + "' already exists");
        }
        
        Application application = new Application(id, name, iconName, executablePath);
        return applicationRepository.save(application);
    }
    
    public Application updateApplication(String id, String name, String iconName, String executablePath) {
        Application application = applicationRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Application with ID '" + id + "' not found"));
        
        application.setName(name);
        application.setIconName(iconName);
        application.setExecutablePath(executablePath);
        
        return applicationRepository.save(application);
    }
    
    public void deleteApplication(String id) {
        Application application = applicationRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Application with ID '" + id + "' not found"));
        
        applicationRepository.delete(application);
    }
    
    public Application getApplicationById(String id) {
        return applicationRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Application with ID '" + id + "' not found"));
    }
    
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }
    
    public void launchApplication(String id) {
        Application application = getApplicationById(id);
        System.out.println("Launching application: " + application.getName());
        System.out.println("Executable path: " + application.getExecutablePath());
        // In a real implementation, this would execute the application
    }
}
