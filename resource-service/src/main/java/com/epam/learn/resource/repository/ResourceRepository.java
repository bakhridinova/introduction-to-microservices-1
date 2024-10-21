package com.epam.learn.resource.repository;

import com.epam.learn.resource.model.Resource;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, UUID> {
}