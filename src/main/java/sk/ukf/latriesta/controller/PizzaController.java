package sk.ukf.latriesta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sk.ukf.latriesta.entity.Pizza;
import sk.ukf.latriesta.service.pizza.PizzaService;

@Controller
public class PizzaController {
    private final PizzaService pizzaService;

    @Autowired
    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping("/pizza/{slug}")
    public String pizzaDetail(@PathVariable String slug, Model model) {
        Pizza pizza = pizzaService.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Pizza not found"));
        model.addAttribute("pizza", pizza);
        return "pizza";
    }
}