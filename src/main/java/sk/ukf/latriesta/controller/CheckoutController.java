package sk.ukf.latriesta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sk.ukf.latriesta.entity.Order;
import sk.ukf.latriesta.entity.User;
import sk.ukf.latriesta.repository.UserRepository;
import sk.ukf.latriesta.service.user.CheckoutService;

import java.security.Principal;

@Controller
public class CheckoutController {

    private final CheckoutService checkoutService;
    private final UserRepository userRepo;

    @Autowired
    public CheckoutController(CheckoutService checkoutService, UserRepository userRepo) {
        this.checkoutService = checkoutService;
        this.userRepo = userRepo;
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam(value = "note", required = false) String note,
                           Principal principal,
                           RedirectAttributes redirectAttributes) {
        User user = userRepo.findByEmail(principal.getName()).orElseThrow();
        Order order = checkoutService.checkout(user, note);
        redirectAttributes.addFlashAttribute("orderId", order.getId());
        return "redirect:/checkout/success";
    }

    @GetMapping("/checkout/success")
    public String checkoutSuccess(Model model) {
        return "checkout/success";
    }
}