package com.cpagoui_code.smart_clinic.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class AppController {

    // Handles GET requests to the root URL ("/") and returns the "welcome.html" template
    @GetMapping("/home")
    public String welcome(Model model) {
        model.addAttribute("message", "Welcome to Spring Boot with Thymeleaf!");
        return "welcome"; // Corresponds to src/main/resources/templates/welcome.html
    }

    // Handles GET requests to "/details/{id}" with a path variable
    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable("id") Long id, Model model) {
        // Fetch data based on the id and add to the model
        model.addAttribute("detailId", id);
        return "details"; // Corresponds to src/main/resources/templates/details.html
    }

    
    // // Handles form submission via POST request to "/save"
    // @PostMapping("/save")
    // public String saveItem(@ModelAttribute("itemForm") ItemForm itemForm) {
       
    //     return "redirect:/";
    // }
}
