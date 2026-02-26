package com.webtesting.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping()
@RequiredArgsConstructor
@Slf4j
public class PageController {
    
    @GetMapping("/software")
    public String showSoftwarePage(Model model) {
        log.info("GET /software - Showing software page");
        model.addAttribute("title", "Software");
        return "software";
    }
    
    @GetMapping("/support")
    public String showSupportPage(Model model) {
        log.info("GET /support - Showing support page");
        model.addAttribute("title", "Support");
        return "support";
    }
}
