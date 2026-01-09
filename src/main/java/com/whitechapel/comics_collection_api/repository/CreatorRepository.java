package com.whitechapel.comics_collection_api.repository;

import com.whitechapel.comics_collection_api.entity.Creator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreatorRepository extends JpaRepository<Creator, Long> {
}