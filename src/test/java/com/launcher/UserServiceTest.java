package com.launcher;

import com.launcher.domain.User;
import com.launcher.repository.EntityManagerProvider;
import com.launcher.service.UserService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    
    private static EntityManager em;
    private UserService userService;
    
    @BeforeAll
    public static void setupClass() {
        em = EntityManagerProvider.getEntityManager();
    }
    
    @BeforeEach
    public void setup() {
        userService = new UserService(em);
    }
    
    @Test
    public void testCreateUser() {
        String userId = "test-user-" + System.currentTimeMillis();
        User user = userService.createUser(userId, "Test User");
        
        assertNotNull(user);
        assertEquals(userId, user.getId());
        assertEquals("Test User", user.getName());
    }
    
    @Test
    public void testGetUserById() {
        String userId = "test-user-get-" + System.currentTimeMillis();
        userService.createUser(userId, "Get Test User");
        
        User retrievedUser = userService.getUserById(userId);
        
        assertNotNull(retrievedUser);
        assertEquals(userId, retrievedUser.getId());
        assertEquals("Get Test User", retrievedUser.getName());
    }
    
    @Test
    public void testUpdateUser() {
        String userId = "test-user-update-" + System.currentTimeMillis();
        userService.createUser(userId, "Original Name");
        
        userService.updateUser(userId, "Updated Name");
        User updatedUser = userService.getUserById(userId);
        
        assertEquals("Updated Name", updatedUser.getName());
    }
    
    @Test
    public void testDuplicateUserThrowsException() {
        String userId = "test-user-duplicate-" + System.currentTimeMillis();
        userService.createUser(userId, "First User");
        
        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(userId, "Duplicate User");
        });
    }
    
    @AfterAll
    public static void teardownClass() {
        EntityManagerProvider.close();
    }
}
