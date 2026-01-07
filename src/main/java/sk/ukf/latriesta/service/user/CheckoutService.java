package sk.ukf.latriesta.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.ukf.latriesta.entity.*;
import sk.ukf.latriesta.repository.CartItemRepository;
import sk.ukf.latriesta.repository.OrderItemRepository;
import sk.ukf.latriesta.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CheckoutService {
    private final CartItemRepository cartItemRepo;
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;

    @Autowired
    public CheckoutService(CartItemRepository cartItemRepo,
                           OrderRepository orderRepo,
                           OrderItemRepository orderItemRepo) {
        this.cartItemRepo = cartItemRepo;
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
    }

    @Transactional
    public Order checkout(User user, String note) {
        List<CartItem> cartItems = cartItemRepo.findByUserId(user.getId());
        if (cartItems.isEmpty()) throw new RuntimeException("Košík je prázdny.");

        Order order = new Order();
        order.setUser(user);
        order.setStatus("pending");
        order.setCreatedAt(LocalDateTime.now());
        order.setTotalPrice(getCartTotal(cartItems));
        order.setNote(note);
        order = orderRepo.save(order);

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem ci : cartItems) {
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setPizzaItem(ci.getPizzaItem());
            oi.setItemName(ci.getPizzaItem().getPizza().getName());
            oi.setItemSize(ci.getPizzaItem().getSize().getName());
            oi.setItemPrice(ci.getPizzaItem().getPrice());
            oi.setQuantity(ci.getQuantity());
            orderItems.add(oi);
        }
        orderItemRepo.saveAll(orderItems);

        cartItemRepo.deleteByUserId(user.getId());
        return order;
    }

    private BigDecimal getCartTotal(List<CartItem> items) {
        return items.stream()
                .map(ci -> ci.getPizzaItem().getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}