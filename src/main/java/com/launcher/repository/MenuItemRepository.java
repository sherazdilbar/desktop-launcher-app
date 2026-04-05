package com.launcher.repository;

import com.launcher.domain.Menu;
import com.launcher.domain.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, String> {
    List<MenuItem> findByMenuOrderByDisplayOrder(Menu menu);
}
