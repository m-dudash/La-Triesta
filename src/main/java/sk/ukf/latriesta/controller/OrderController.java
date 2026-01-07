package sk.ukf.latriesta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sk.ukf.latriesta.entity.Order;
import sk.ukf.latriesta.entity.User;
import sk.ukf.latriesta.repository.OrderRepository;
import sk.ukf.latriesta.repository.UserRepository;
import sk.ukf.latriesta.service.user.OrderService;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {

    private final OrderRepository orderRepo;
    private final UserRepository userRepo;
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderRepository orderRepo, UserRepository userRepo, OrderService orderService) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.orderService = orderService;
    }

    @GetMapping("/orders/my")
    public String myOrders(Model model, Principal principal) {
        User user = userRepo.findByEmail(principal.getName()).orElseThrow();
        List<Order> orders = orderRepo.findByUserId(user.getId());

        int weekCount = orderService.userOrdersLastWeek(user.getId()).size();
        int monthCount = orderService.userOrdersLastMonth(user.getId()).size();
        int yearCount = orderService.userOrdersLastYear(user.getId()).size();
        Double totalSpent = orderService.userTotalSpent(user.getId());

        model.addAttribute("orders", orders);
        model.addAttribute("weekCount", weekCount);
        model.addAttribute("monthCount", monthCount);
        model.addAttribute("yearCount", yearCount);
        model.addAttribute("totalSpent", totalSpent != null ? totalSpent : 0.0);
        return "orders/my";
    }

    @GetMapping("/orders/detail/{id}")
    public String orderDetail(@PathVariable Integer id, Model model, Principal principal) {
        User user = userRepo.findByEmail(principal.getName()).orElseThrow();
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Objednávka nenájdená"));
        if (!order.getUser().getId().equals(user.getId())) {
            return "error/403";
        }
        model.addAttribute("order", order);
        model.addAttribute("orderItems", order.getOrderItems());
        return "orders/detail";
    }

    @PostMapping("/orders/cancel/{id}")
    public String cancelOrder(@PathVariable Integer id, Principal principal, RedirectAttributes redirectAttributes) {
        User user = userRepo.findByEmail(principal.getName()).orElseThrow();
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Objednávka nenájdená"));
        if (!order.getUser().getId().equals(user.getId())) {
            redirectAttributes.addFlashAttribute("error", "K tejto objednávke nemáte prístup.");
            return "redirect:/orders/my";
        }
        if (!"pending".equalsIgnoreCase(order.getStatus())) {
            redirectAttributes.addFlashAttribute("error", "Objednávku možno zrušiť len v stave 'pending'.");
            return "redirect:/orders/detail/" + id;
        }
        order.setStatus("cancelled");
        orderRepo.save(order);
        redirectAttributes.addFlashAttribute("success", "Objednávka úspešne zrušená.");
        return "redirect:/orders/detail/" + id;
    }
}