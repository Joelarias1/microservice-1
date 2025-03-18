package com.sumativa1joelarias.demo.microservices.forums.repository;

import com.sumativa1joelarias.demo.microservices.forums.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
} 