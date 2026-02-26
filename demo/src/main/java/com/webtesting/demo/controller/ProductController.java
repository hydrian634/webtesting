package com.webtesting.demo.controller;

import com.webtesting.demo.dto.ProductResponse;
import com.webtesting.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    
    private final ProductService productService;
    
    @GetMapping
    public String showProductsPage(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(required = false) String category,
                                  @RequestParam(required = false) String search,
                                  Model model) {
        log.info("GET /products - page: {}, category: {}, search: {}", page, category, search);
        
        Pageable pageable = PageRequest.of(page, 12);
        Page<ProductResponse> products;
        
        if (search != null && !search.isEmpty()) {
            log.info("Searching products with keyword: {}", search);
            products = productService.searchProducts(search, pageable);
        } else if (category != null && !category.isEmpty()) {
            log.info("Filtering products by category: {}", category);
            products = productService.getProductsByCategory(category, pageable);
        } else {
            log.info("Fetching all products");
            products = productService.getAllProducts(pageable);
        }
        
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("category", category);
        model.addAttribute("search", search);
        
        return "products";
    }
    
    @GetMapping("/{id}")
    public String showProductDetail(@PathVariable Long id, Model model) {
        log.info("GET /products/{} - Showing product detail", id);
        
        try {
            ProductResponse product = productService.getProductById(id);
            model.addAttribute("product", product);
            return "product-detail";
        } catch (Exception e) {
            log.error("Product not found: {}", id);
            return "redirect:/products?error=Product not found";
        }
    }
}
