package sk.ukf.latriesta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sk.ukf.latriesta.entity.Order;
import sk.ukf.latriesta.entity.User;
import sk.ukf.latriesta.repository.OrderRepository;
import sk.ukf.latriesta.repository.UserRepository;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffOrderController {

    private final OrderRepository orderRepo;
    private final UserRepository userRepo;

    @Autowired
    public StaffOrderController(OrderRepository orderRepo, UserRepository userRepo) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/chef")
    @PreAuthorize("hasRole('CHEF')")
    public String chefOrders(Model model) {
        List<Order> orders = orderRepo.findByStatusInOrderByCreatedAtAsc(List.of("pending", "preparing"));
        model.addAttribute("orders", orders);
        return "staff/chef";
    }

    @PostMapping("/chef/take/{id}")
    @PreAuthorize("hasRole('CHEF')")
    public String chefTakeOrder(@PathVariable Integer id, Principal principal) {
        Order order = orderRepo.findById(id).orElseThrow();
        if (!"pending".equalsIgnoreCase(order.getStatus())) return "redirect:/staff/chef";
        User chef = userRepo.findByEmail(principal.getName()).orElseThrow();
        order.setStatus("preparing");
        order.setChef(chef);
        orderRepo.save(order);
        return "redirect:/staff/chef";
    }

    @PostMapping("/chef/ready/{id}")
    @PreAuthorize("hasRole('CHEF')")
    public String chefOrderReady(@PathVariable Integer id, Principal principal) {
        Order order = orderRepo.findById(id).orElseThrow();
        if (!"preparing".equalsIgnoreCase(order.getStatus())) return "redirect:/staff/chef";
        order.setStatus("ready");
        orderRepo.save(order);
        return "redirect:/staff/chef";
    }



    @GetMapping("/courier")
    @PreAuthorize("hasRole('COURIER')")
    public String courierOrders(Model model) {
        List<Order> orders = orderRepo.findByStatusInOrderByCreatedAtAsc(List.of("ready", "delivering"));
        model.addAttribute("orders", orders);
        return "staff/courier";
    }

    @PostMapping("/courier/take/{id}")
    @PreAuthorize("hasRole('COURIER')")
    public String courierTakeOrder(@PathVariable Integer id, Principal principal) {
        Order order = orderRepo.findById(id).orElseThrow();
        if (!"ready".equalsIgnoreCase(order.getStatus())) return "redirect:/staff/courier";
        User courier = userRepo.findByEmail(principal.getName()).orElseThrow();
        order.setStatus("delivering");
        order.setCourier(courier);
        orderRepo.save(order);
        return "redirect:/staff/courier";
    }

    @PostMapping("/courier/delivered/{id}")
    @PreAuthorize("hasRole('COURIER')")
    public String courierDelivered(@PathVariable Integer id) {
        Order order = orderRepo.findById(id).orElseThrow();
        if (!"delivering".equalsIgnoreCase(order.getStatus())) return "redirect:/staff/courier";
        order.setStatus("delivered");
        orderRepo.save(order);
        return "redirect:/staff/courier";
    }
}