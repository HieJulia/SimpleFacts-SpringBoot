package com.boot.repository;

import com.boot.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * This method returns the first user it finds in the database with a
     * specific name.
     * 
     * @param name - The name of the user
     * @return
     */
    User findFirstByName(String name);
}
