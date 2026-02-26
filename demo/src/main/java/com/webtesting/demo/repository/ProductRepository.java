package com.webtesting.demo.repository;

import com.webtesting.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    Page<Product> findByCategory(String category, Pageable pageable);
    Page<Product> findByIsActiveTrue(Pageable pageable);
    List<Product> findByCategory(String category);
    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
}
