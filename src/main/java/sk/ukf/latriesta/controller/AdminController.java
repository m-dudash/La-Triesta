package sk.ukf.latriesta.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sk.ukf.latriesta.entity.*;
import sk.ukf.latriesta.service.pizza.*;
import sk.ukf.latriesta.service.user.RoleService;
import sk.ukf.latriesta.service.user.UserService;
import sk.ukf.latriesta.service.user.OrderService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final IngredientService ingredientService;
    private final TagService tagService;
    private final SizeService sizeService;
    private final PizzaService pizzaService;
    private final PizzaItemService pizzaItemService;
    private final UserService userService;
    private final OrderService orderService;
    private final RoleService roleService;




    @Autowired
    public AdminController(IngredientService ingredientService,
                           TagService tagService,
                           SizeService sizeService,
                           PizzaService pizzaService,
                           PizzaItemService pizzaItemService,
                           UserService userService,
                           RoleService roleService,
                           OrderService orderService) {
        this.ingredientService = ingredientService;
        this.tagService = tagService;
        this.sizeService = sizeService;
        this.pizzaService = pizzaService;
        this.pizzaItemService = pizzaItemService;
        this.userService = userService;
        this.roleService = roleService;
        this.orderService = orderService;
    }

    // ==================== DASHBOARD ====================
    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("ingredientsCount", ingredientService.findAll().size());
        model.addAttribute("tagsCount", tagService.findAll().size());
        model.addAttribute("sizesCount", sizeService.findAll().size());
        model.addAttribute("pizzasCount", pizzaService.findAll().size());
        model.addAttribute("usersCount", userService.findAll().size());
        model.addAttribute("ordersCount", orderService.findAll().size());
        return "admin/dashboard";
    }

    // ==================== INGREDIENTS ====================
    @GetMapping("/ingredients")
    public String listIngredients(Model model) {
        model.addAttribute("ingredients", ingredientService.findAll());
        return "admin/ingredients/list";
    }

    @GetMapping("/ingredients/new")
    public String newIngredient(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        return "admin/ingredients/form";
    }

    @GetMapping("/ingredients/edit/{id}")
    public String editIngredient(@PathVariable Integer id, Model model) {
        Ingredient ingredient = ingredientService.findById(id)
                .orElseThrow(() -> new RuntimeException("Prísada nenájdená"));
        model.addAttribute("ingredient", ingredient);
        return "admin/ingredients/form";
    }

    @PostMapping("/ingredients/save")
    public String saveIngredient(@Valid @ModelAttribute Ingredient ingredient,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/ingredients/form";
        }
        ingredientService.save(ingredient);
        redirectAttributes.addFlashAttribute("success", "Prísada úspešne uložená");
        return "redirect:/admin/ingredients";
    }

    @PostMapping("/ingredients/delete/{id}")
    public String deleteIngredient(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        ingredientService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Prísada úspešne vymazaná");
        return "redirect:/admin/ingredients";
    }

    // ==================== TAGS ====================
    @GetMapping("/tags")
    public String listTags(Model model) {
        model.addAttribute("tags", tagService.findAll());
        return "admin/tags/list";
    }

    @GetMapping("/tags/new")
    public String newTag(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags/form";
    }

    @GetMapping("/tags/edit/{id}")
    public String editTag(@PathVariable Integer id, Model model) {
        Tag tag = tagService.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag nenájdený"));
        model.addAttribute("tag", tag);
        return "admin/tags/form";
    }

    @PostMapping("/tags/save")
    public String saveTag(@Valid @ModelAttribute Tag tag,
                          BindingResult result,
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/tags/form";
        }
        tagService. save(tag);
        redirectAttributes.addFlashAttribute("success", "Tag úspešne uložený");
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/delete/{id}")
    public String deleteTag(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        tagService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Tag úspešne vymazaný");
        return "redirect:/admin/tags";
    }

    // ==================== SIZES ====================
    @GetMapping("/sizes")
    public String listSizes(Model model) {
        model.addAttribute("sizes", sizeService.findAll());
        return "admin/sizes/list";
    }

    @GetMapping("/sizes/new")
    public String newSize(Model model) {
        model.addAttribute("size", new Size());
        return "admin/sizes/form";
    }

    @GetMapping("/sizes/edit/{id}")
    public String editSize(@PathVariable Integer id, Model model) {
        Size size = sizeService.findById(id)
                .orElseThrow(() -> new RuntimeException("Veľkosť nenájdená"));
        model.addAttribute("size", size);
        return "admin/sizes/form";
    }

    @PostMapping("/sizes/save")
    public String saveSize(@Valid @ModelAttribute Size size,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/sizes/form";
        }
        sizeService. save(size);
        redirectAttributes.addFlashAttribute("success", "Veľkosť úspešne uložená");
        return "redirect:/admin/sizes";
    }


    @PostMapping("/sizes/delete/{id}")
    public String deleteSize(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            sizeService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Veľkosť bola odstránená (soft-delete).");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Nepodarilo sa vymazať veľkosť: " + ex.getMessage());
        }
        return "redirect:/admin/sizes";
    }

    // ==================== PIZZAS ====================
    @GetMapping("/pizzas")
    public String listPizzas(Model model) {
        model.addAttribute("pizzas", pizzaService.findAll());
        return "admin/pizzas/list";
    }

    @GetMapping("/pizzas/new")
    public String newPizza(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("allIngredients", ingredientService.findAll());
        model.addAttribute("allTags", tagService.findAll());
        model.addAttribute("allSizes", sizeService.findAll());
        return "admin/pizzas/form";
    }

    @GetMapping("/pizzas/edit/{id}")
    public String editPizza(@PathVariable Integer id, Model model) {
        Pizza pizza = pizzaService.findById(id)
                .orElseThrow(() -> new RuntimeException("Pizza nenájdená"));

        List<Size> allSizes = sizeService.findAll();

        Set<Integer> activeSizeIds = pizza.getPizzaItems().stream()
                .filter(item -> item.getActive() != null && item.getActive())
                .map(item -> item.getSize().getId())
                .collect(Collectors.toSet());

        List<Size> availableSizes = allSizes.stream()
                .filter(size -> !activeSizeIds.contains(size.getId()))
                .collect(Collectors.toList());

        model.addAttribute("pizza", pizza);
        model.addAttribute("allIngredients", ingredientService.findAll());
        model.addAttribute("allTags", tagService.findAll());
        model.addAttribute("allSizes", allSizes);
        model.addAttribute("availableSizes", availableSizes);
        model.addAttribute("pizzaItems", pizza.getPizzaItems());

        return "admin/pizzas/form";
    }

    @PostMapping("/pizzas/save")
    public String savePizza(@ModelAttribute @Valid Pizza pizza,
                            BindingResult bindingResult,
                            @RequestParam(required = false) List<Integer> ingredientIds,
                            @RequestParam(required = false) List<Integer> tagIds,
                            @RequestParam(required = false) Integer sizeId,
                            @RequestParam(required = false) BigDecimal price,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        boolean isNew = pizza.getId() == null;

        if (isNew && (sizeId == null || price == null || price.compareTo(BigDecimal.ZERO) <= 0)) {
            bindingResult.rejectValue("pizzaItems", "error.pizza", "Musíte pridať aspoň jednu veľkosť s cenou.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("allIngredients", ingredientService.findAll());
            model.addAttribute("allTags", tagService.findAll());
            model.addAttribute("allSizes", sizeService.findAll());
            if (!isNew) {
                model.addAttribute("pizzaItems", pizza.getPizzaItems());
            }
            return "admin/pizzas/form";
        }

        if (isNew && sizeId != null && price != null) {
            Size size = sizeService.findById(sizeId)
                    .orElseThrow(() -> new RuntimeException("Veľkosť nenájdená"));
            PizzaItem item = new PizzaItem();
            item.setSize(size);
            item.setPrice(price);
            item.setActive(true);
            pizza.addPizzaItem(item);
        }

        Pizza savedPizza = pizzaService.save(pizza, ingredientIds, tagIds);
        redirectAttributes.addFlashAttribute("success", "Pizza uložená");
        return "redirect:/admin/pizzas/edit/" + savedPizza.getId();
    }

    @PostMapping("/pizzas/delete/{id}")
    public String deletePizza(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            pizzaService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Pizza bola odstránená.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Nepodarilo sa vymazať pizzu: " + ex.getMessage());
        }
        return "redirect:/admin/pizzas";
    }

    @PostMapping("/pizzas/toggle-available/{id}")
    public String togglePizzaAvailability(@PathVariable Integer id,
                                          RedirectAttributes redirectAttributes) {
        Pizza pizza = pizzaService.findById(id)
                .orElseThrow(() -> new RuntimeException("Pizza nenájdená"));

        pizza.setAvailable(!pizza.getAvailable());
        pizzaService.save(pizza);

        redirectAttributes.addFlashAttribute("success",
                pizza.getAvailable() ? "Pizza je teraz dostupná" : "Pizza je skrytá");

        return "redirect:/admin/pizzas";
    }

    // ==================== PIZZA ITEMS ====================
    @PostMapping("/pizzas/{pizzaId}/items/save")
    public String savePizzaItem(@PathVariable Integer pizzaId,
                                @RequestParam Integer sizeId,
                                @RequestParam BigDecimal price,
                                RedirectAttributes redirectAttributes) {

        if (sizeId == null || price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            redirectAttributes.addFlashAttribute("error", "Neplatné údaje pre veľkosť alebo cenu.");
            return "redirect:/admin/pizzas/edit/" + pizzaId;
        }

        Pizza pizza = pizzaService.findById(pizzaId)
                .orElseThrow(() -> new RuntimeException("Pizza nenájdená"));

        Size size = sizeService.findById(sizeId)
                .orElseThrow(() -> new RuntimeException("Veľkosť nenájdená"));

        boolean alreadyActive = pizza.getPizzaItems().stream()
                .anyMatch(i -> i.getSize().getId().equals(sizeId) &&
                        i.getActive() != null && i.getActive());

        if (alreadyActive) {
            redirectAttributes.addFlashAttribute("error", "Táto veľkosť už je aktívna.");
            return "redirect:/admin/pizzas/edit/" + pizzaId;
        }

        PizzaItem item = new PizzaItem();
        item.setPizza(pizza);
        item.setSize(size);
        item.setPrice(price);
        item.setActive(true);

        pizzaItemService.save(item);

        redirectAttributes.addFlashAttribute("success", "Cena pre veľkosť pridaná");
        return "redirect:/admin/pizzas/edit/" + pizzaId;
    }

    @PostMapping("/pizzas/items/delete/{id}")
    public String deletePizzaItem(@PathVariable Integer id,
                                  @RequestParam Integer pizzaId,
                                  RedirectAttributes redirectAttributes) {

        Pizza pizza = pizzaService.findById(pizzaId)
                .orElseThrow(() -> new RuntimeException("Pizza nenájdená"));

        pizza.getPizzaItems()
                .stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .ifPresent(i -> i.setActive(false));

        pizzaService.save(pizza);

        redirectAttributes.addFlashAttribute("success", "Veľkosť skrytá");
        return "redirect:/admin/pizzas/edit/" + pizzaId;
    }

    @PostMapping("/pizzas/items/restore/{id}")
    public String restorePizzaItem(@PathVariable Integer id,
                                   @RequestParam Integer pizzaId,
                                   RedirectAttributes redirectAttributes) {

        Pizza pizza = pizzaService.findById(pizzaId)
                .orElseThrow(() -> new RuntimeException("Pizza nenájdená"));

        PizzaItem itemToRestore = pizza.getPizzaItems().stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("PizzaItem nenájdený"));

        Integer sizeId = itemToRestore.getSize().getId();

        boolean alreadyActiveExists = pizza.getPizzaItems().stream()
                .anyMatch(i -> !i.getId().equals(id)
                        && i.getSize().getId().equals(sizeId)
                        && i.getActive() != null && i.getActive());

        if (alreadyActiveExists) {
            redirectAttributes.addFlashAttribute("error", "Nemôžete obnoviť — už existuje aktívna veľkosť s rovnakým rozmerom.");
            return "redirect:/admin/pizzas/edit/" + pizzaId;
        }

        itemToRestore.setActive(true);
        pizzaService.save(pizza);

        redirectAttributes.addFlashAttribute("success", "Veľkosť obnovená");
        return "redirect:/admin/pizzas/edit/" + pizzaId;
    }

    // ==================== USERS ====================

    @GetMapping("/users")
    public String listUsers(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size) {
        var userPage = userService.findAll(page, size);
        model.addAttribute("users", userPage.getContent());
        model.addAttribute("page", userPage);
        model.addAttribute("currentPage", userPage.getNumber());
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("pageSize", size);
        return "admin/users/list";
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.findAll());
        return "admin/users/form";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable Integer id, Model model) {
        User user = userService.findById(id).orElseThrow(() -> new RuntimeException("Používateľ nenájdený"));
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.findAll());
        return "admin/users/form";
    }

    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute User user,
                           BindingResult result,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        boolean isNew = (user.getId() == null);

        if (isNew) {
            if (user.getPassword() == null || user.getPassword().trim().length() < 8) {
                result.rejectValue("password", "error.user", "Heslo je povinné a musí mať aspoň 8 znakov");
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("allRoles", roleService.findAll());
            return "admin/users/form";
        }

        if (isNew) {
            userService.save(user);
        } else {
            User existingUser = userService.findById(user.getId())
                    .orElseThrow(() -> new RuntimeException("Používateľ nenájdený"));
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setAddress(user. getAddress());
            existingUser.setPhone(user.getPhone());
            existingUser.setRole(user.getRole());

            if (user.getPassword() != null && !user.getPassword().isBlank()) {
                existingUser.setPassword(user.getPassword());
            }

            userService. save(existingUser);
        }

        redirectAttributes.addFlashAttribute("success", "Používateľ uložený");
        return "redirect:/admin/users";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        userService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Používateľ vymazaný");
        return "redirect:/admin/users";
    }


    // ==================== ORDERS ====================

    @GetMapping("/orders")
    public String listOrders(Model model,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size) {
        var orderPage = orderService.findAll(page, size);
        model.addAttribute("orders", orderPage.getContent());
        model.addAttribute("page", orderPage);
        model.addAttribute("currentPage", orderPage.getNumber());
        model.addAttribute("totalPages", orderPage.getTotalPages());
        model.addAttribute("pageSize", size);
        return "admin/orders/list";
    }

    @GetMapping("/orders/{id}")
    public String orderDetail(@PathVariable Integer id, Model model) {
        Order order = orderService.findById(id)
                .orElseThrow(() -> new RuntimeException("Objednávka nenájdená"));
        model.addAttribute("order", order);
        model.addAttribute("orderItems", order.getOrderItems());
        return "admin/orders/detail";
    }

    @PostMapping("/orders/{id}/status")
    public String changeOrderStatus(@PathVariable Integer id,
                                    @RequestParam String status,
                                    RedirectAttributes redirectAttributes) {
        Order order = orderService.findById(id)
                .orElseThrow(() -> new RuntimeException("Objednávka nenájdená"));
        order.setStatus(status);
        orderService.save(order);
        redirectAttributes.addFlashAttribute("success", "Status upravený.");
        return "redirect:/admin/orders/" + id;
    }

    @PostMapping("/orders/{id}/delete")
    public String deleteOrder(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        orderService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Objednávka trvalo zmazaná.");
        return "redirect:/admin/orders";
    }
}
