package sk.ukf.latriesta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sk.ukf.latriesta.entity.Pizza;
import sk.ukf.latriesta.entity.Tag;
import sk.ukf.latriesta.service.pizza.PizzaService;
import sk.ukf.latriesta.service.pizza.TagService;

import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController {

    private final PizzaService pizzaService;
    private final TagService tagService;

    @Autowired
    public MenuController(PizzaService pizzaService, TagService tagService) {
        this.pizzaService = pizzaService;
        this.tagService = tagService;
    }

    @GetMapping
    public String menuPage(@RequestParam(value = "search", required = false) String search,
                           @RequestParam(value = "tag", required = false) String tagName,
                           Model model) {
        List<Pizza> pizzas;

        if (search != null && !search.isBlank()) {
            pizzas = pizzaService.searchByKeyword(search);
        } else if (tagName != null && !tagName.isBlank()) {
            pizzas = pizzaService.findByTagName(tagName);
        } else {
            pizzas = pizzaService.findAvailable();
        }

        model.addAttribute("pizzas", pizzas);
        model.addAttribute("tags", tagService.findAll());
        model.addAttribute("search", search);
        model.addAttribute("selectedTag", tagName);

        return "menu";
    }
}