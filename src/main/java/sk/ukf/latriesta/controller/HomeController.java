package sk.ukf.latriesta.controller;
import org.springframework.beans.factory.annotation.Autowired;
import sk.ukf.latriesta.service.pizza.PizzaService;
import sk.ukf.latriesta.service.user.OrderService;
import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;




import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {

    private final OrderService orderService;
    private final PizzaService pizzaService;


    @Autowired
    public HomeController(OrderService orderService, PizzaService pizzaService) {

        this.orderService = orderService;
        this.pizzaService = pizzaService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("pizzas", pizzaService.findAvailable());
        return "home";
    }

    // testovacie endpointy pre chyby
    @GetMapping("/error500")
    public void error500() { throw new RuntimeException("test 500"); }

    @GetMapping("/error403")
    public void error403() {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
    }

    @GetMapping("/error404")
    public void error404() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
    }


    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @ModelAttribute("pendingCount")
    public Long pendingCount() {
        Long count = orderService.countPendingOrders();
        return count != null ? count : 0L;
    }
}