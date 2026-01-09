package com.whitechapel.comics_collection_api.repository;

import com.whitechapel.comics_collection_api.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}