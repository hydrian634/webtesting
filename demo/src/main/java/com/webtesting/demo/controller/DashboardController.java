package com.webtesting.demo.controller;

import com.webtesting.demo.dto.AddressResponse;
import com.webtesting.demo.dto.OrderResponse;
import com.webtesting.demo.service.AddressService;
import com.webtesting.demo.service.OrderService;
import com.webtesting.demo.service.UserService;
import com.webtesting.demo.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@Slf4j
public class DashboardController {
    
    private final UserService userService;
    private final AddressService addressService;
    private final OrderService orderService;
    
    @GetMapping
    public String showDashboard(Model model) {
        log.info("GET /dashboard - Showing dashboard");
        // Note: In real implementation, get current user from SecurityContext
        model.addAttribute("title", "My Account");
        return "dashboard";
    }
    
    @GetMapping("/account")
    public String showAccountInfo(@RequestParam Long userId, Model model) {
        log.info("GET /dashboard/account - userId: {}", userId);
        
        try {
            UserResponse user = userService.getUserById(userId);
            model.addAttribute("user", user);
            model.addAttribute("title", "Account Information");
            return "account-info";
        } catch (Exception e) {
            log.error("Error loading account information: {}", e.getMessage());
            return "redirect:/dashboard";
        }
    }
    
    @GetMapping("/addresses")
    public String showAddresses(@RequestParam Long userId, Model model) {
        log.info("GET /dashboard/addresses - userId: {}", userId);
        
        try {
            UserResponse user = userService.getUserById(userId);
            // Note: In real implementation, fetch addresses for the current user
            model.addAttribute("user", user);
            model.addAttribute("title", "Address Book");
            return "address-book";
        } catch (Exception e) {
            log.error("Error loading addresses: {}", e.getMessage());
            return "redirect:/dashboard";
        }
    }
    
    @GetMapping("/orders")
    public String showMyOrders(@RequestParam Long userId, Model model) {
        log.info("GET /dashboard/orders - userId: {}", userId);
        
        try {
            UserResponse user = userService.getUserById(userId);
            // Note: In real implementation, fetch orders for the current user
            model.addAttribute("user", user);
            model.addAttribute("title", "My Orders");
            return "my-orders";
        } catch (Exception e) {
            log.error("Error loading orders: {}", e.getMessage());
            return "redirect:/dashboard";
        }
    }
}
