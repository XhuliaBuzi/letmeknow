package com.letmeknow.repository;

import com.letmeknow.model.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewsRepository extends JpaRepository<Reviews, UUID> {
}
