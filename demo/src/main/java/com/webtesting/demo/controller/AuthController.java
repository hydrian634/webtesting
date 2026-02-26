package com.webtesting.demo.controller;

import com.webtesting.demo.dto.LoginRequest;
import com.webtesting.demo.dto.SignUpRequest;
import com.webtesting.demo.dto.UserResponse;
import com.webtesting.demo.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping()
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    
    private final AuthService authService;
    
    @GetMapping("/signin")
    public String showSignInPage(Model model) {
        log.info("GET /signin - Displaying sign in page");
        model.addAttribute("loginRequest", new LoginRequest());
        return "signin";
    }
    
    @PostMapping("/signin")
    public String handleSignIn(@Valid @ModelAttribute LoginRequest loginRequest, 
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        log.info("POST /signin - Processing sign in");
        
        if (bindingResult.hasErrors()) {
            log.warn("Sign in validation errors: {}", bindingResult.getAllErrors());
            return "signin";
        }
        
        try {
            UserResponse user = authService.login(loginRequest);
            redirectAttributes.addFlashAttribute("message", "Login successful!");
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/dashboard";
        } catch (Exception e) {
            log.error("Sign in error: {}", e.getMessage());
            bindingResult.reject("login.error", e.getMessage());
            return "signin";
        }
    }
    
    @GetMapping("/signup")
    public String showSignUpPage(Model model) {
        log.info("GET /signup - Displaying sign up page");
        model.addAttribute("signUpRequest", new SignUpRequest());
        return "signup";
    }
    
    @PostMapping("/signup")
    public String handleSignUp(@Valid @ModelAttribute SignUpRequest signUpRequest,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        log.info("POST /signup - Processing sign up");
        
        if (bindingResult.hasErrors()) {
            log.warn("Sign up validation errors: {}", bindingResult.getAllErrors());
            return "signup";
        }
        
        try {
            UserResponse user = authService.signup(signUpRequest);
            redirectAttributes.addFlashAttribute("message", "Registration successful! Please sign in.");
            return "redirect:/signin";
        } catch (Exception e) {
            log.error("Sign up error: {}", e.getMessage());
            bindingResult.reject("signup.error", e.getMessage());
            return "signup";
        }
    }
    
    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/products";
    }
}
