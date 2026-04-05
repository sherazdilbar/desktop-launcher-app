package com.launcher.service;

import com.launcher.domain.Application;
import com.launcher.domain.Menu;
import com.launcher.domain.MenuItem;
import com.launcher.domain.User;
import com.launcher.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class MenuService {
    
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;
    
    public MenuService(MenuRepository menuRepository, MenuItemRepository menuItemRepository,
                      UserRepository userRepository, ApplicationRepository applicationRepository) {
        this.menuRepository = menuRepository;
        this.menuItemRepository = menuItemRepository;
        this.userRepository = userRepository;
        this.applicationRepository = applicationRepository;
    }
    
    public Menu createMenu(String id, String name, String userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User with ID '" + userId + "' not found"));
        
        Menu menu = new Menu(id, name);
        menu.setUser(user);
        
        return menuRepository.save(menu);
    }
    
    public Menu createSubmenu(String id, String name, String parentMenuId) {
        Menu parentMenu = menuRepository.findById(parentMenuId)
            .orElseThrow(() -> new IllegalArgumentException("Parent menu with ID '" + parentMenuId + "' not found"));
        
        Menu submenu = new Menu(id, name);
        submenu.setParentMenu(parentMenu);
        submenu.setUser(parentMenu.getUser());
        
        return menuRepository.save(submenu);
    }
    
    public void deleteMenu(String id) {
        Menu menu = menuRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Menu with ID '" + id + "' not found"));
        
        menuRepository.delete(menu);
    }
    
    public Menu getMenuById(String id) {
        return menuRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Menu with ID '" + id + "' not found"));
    }
    
    public List<Menu> getRootMenusForUser(String userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User with ID '" + userId + "' not found"));
        return menuRepository.findByUserAndParentMenuIsNull(user);
    }
    
    public List<Menu> getSubmenus(String menuId) {
        Menu menu = getMenuById(menuId);
        return menuRepository.findByParentMenu(menu);
    }
    
    public MenuItem addMenuItem(String menuId, String applicationId, String name, int order) {
        Menu menu = getMenuById(menuId);
        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new IllegalArgumentException("Application with ID '" + applicationId + "' not found"));
        
        String menuItemId = "mi-" + menuId + "-" + applicationId;
        MenuItem menuItem = new MenuItem(menuItemId, name, order);
        menuItem.setMenu(menu);
        menuItem.setApplication(application);
        
        return menuItemRepository.save(menuItem);
    }
    
    public void removeMenuItem(String menuItemId) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
            .orElseThrow(() -> new IllegalArgumentException("MenuItem with ID '" + menuItemId + "' not found"));
        
        menuItemRepository.delete(menuItem);
    }
    
    public List<MenuItem> getMenuItems(String menuId) {
        Menu menu = getMenuById(menuId);
        return menuItemRepository.findByMenuOrderByDisplayOrder(menu);
    }
}
