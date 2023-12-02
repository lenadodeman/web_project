package com.example.web_project.api.repository;

import com.example.web_project.api.model.dbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<dbUser, Long> {

    dbUser findByUsername(String username);

}
