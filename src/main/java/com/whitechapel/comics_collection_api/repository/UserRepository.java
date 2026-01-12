package com.whitechapel.comics_collection_api.repository;

import com.whitechapel.comics_collection_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}