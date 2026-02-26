package com.webtesting.demo.service;

import com.webtesting.demo.dto.ProductResponse;
import com.webtesting.demo.model.Product;
import com.webtesting.demo.repository.ProductRepository;
import com.webtesting.demo.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    
    private final ProductRepository productRepository;
    
    public ProductResponse getProductById(Long id) {
        log.info("Fetching product with id: {}", id);
        return productRepository.findById(id)
            .map(this::convertToResponse)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }
    
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        log.info("Fetching all active products with pagination");
        return productRepository.findByIsActiveTrue(pageable)
            .map(this::convertToResponse);
    }
    
    public Page<ProductResponse> searchProducts(String keyword, Pageable pageable) {
        log.info("Searching products with keyword: {}", keyword);
        return productRepository.findByNameContainingIgnoreCase(keyword, pageable)
            .map(this::convertToResponse);
    }
    
    public Page<ProductResponse> getProductsByCategory(String category, Pageable pageable) {
        log.info("Fetching products by category: {}", category);
        return productRepository.findByCategory(category, pageable)
            .map(this::convertToResponse);
    }
    
    public List<ProductResponse> getCategorizedProducts(String category) {
        log.info("Fetching categorized products: {}", category);
        return productRepository.findByCategory(category)
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }
    
    private ProductResponse convertToResponse(Product product) {
        return ProductResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .category(product.getCategory())
            .imageUrl(product.getImageUrl())
            .stockQuantity(product.getStockQuantity())
            .isActive(product.getIsActive())
            .build();
    }
}
