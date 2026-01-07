package sk.ukf.latriesta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sk.ukf.latriesta.entity.CartItem;
import sk.ukf.latriesta.entity.User;
import sk.ukf.latriesta.repository.UserRepository;
import sk.ukf.latriesta.service.user.CartService;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final UserRepository userRepo;

    @Autowired
    public CartController(CartService cartService, UserRepository userRepo) {
        this.cartService = cartService;
        this.userRepo = userRepo;
    }

    @GetMapping
    public String viewCart(Model model, Principal principal) {
        User user = userRepo.findByEmail(principal.getName()).orElseThrow();
        List<CartItem> cartItems = cartService.getCart(user);
        BigDecimal cartTotal = cartItems.stream()
                .map(ci -> ci.getPizzaItem().getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("user", user);
        model.addAttribute("cartTotal", cartTotal);
        return "cart/view";
    }


    @PostMapping("/add")
    public String addToCart(@RequestParam Integer pizzaItemId,
                            @RequestParam(defaultValue = "1") int quantity,
                            Principal principal,
                            RedirectAttributes redirectAttributes) {
        User user = userRepo.findByEmail(principal.getName()).orElseThrow();
        try {
            cartService.addToCart(user, pizzaItemId, quantity);
            redirectAttributes.addFlashAttribute("success", "Pridané do košíka.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/cart";
    }


    @PostMapping("/remove/{cartItemId}")
    public String removeFromCart(@PathVariable Integer cartItemId, Principal principal) {
        User user = userRepo.findByEmail(principal.getName()).orElseThrow();
        cartService.removeFromCart(user, cartItemId);
        return "redirect:/cart";
    }
}