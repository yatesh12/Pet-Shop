package com.petshop.web.controller;

import com.petshop.shared.dto.HomePageDto;
import com.petshop.web.service.CatalogClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CatalogClient catalogClient;

    @GetMapping("/")
    public String home(Model model) {
        HomePageDto home = catalogClient.home();
        model.addAttribute("home", home);
        model.addAttribute("promotions", catalogClient.promotions());
        return "home/index";
    }
}

