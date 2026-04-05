package com.launcher.repository;

import com.launcher.domain.Menu;
import com.launcher.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {
    List<Menu> findByUserAndParentMenuIsNull(User user);
    List<Menu> findByParentMenu(Menu parentMenu);
}
