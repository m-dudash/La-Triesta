package sk.ukf.latriesta.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.ukf.latriesta.entity.CartItem;
import sk.ukf.latriesta.entity.PizzaItem;
import sk.ukf.latriesta.entity.User;
import sk.ukf.latriesta.repository.CartItemRepository;
import sk.ukf.latriesta.repository.PizzaItemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartItemRepository cartItemRepo;
    private final PizzaItemRepository pizzaItemRepo;

    @Autowired
    public CartService(CartItemRepository cartItemRepo, PizzaItemRepository pizzaItemRepo) {
        this.cartItemRepo = cartItemRepo;
        this.pizzaItemRepo = pizzaItemRepo;
    }

    public List<CartItem> getCart(User user) {
        return cartItemRepo.findByUserId(user.getId());
    }

    public void addToCart(User user, Integer pizzaItemId, int quantity) {
        final int MAX_QUANTITY = 10;

        Optional<CartItem> found = cartItemRepo.findByUserIdAndPizzaItemId(user.getId(), pizzaItemId);
        PizzaItem pizzaItem = pizzaItemRepo.findById(pizzaItemId)
                .orElseThrow(() -> new IllegalArgumentException("PizzaItem not found"));

        int existing = found.map(CartItem::getQuantity).orElse(0);
        int toSet = Math.min(existing + quantity, MAX_QUANTITY);

        if (existing == MAX_QUANTITY) {
            throw new IllegalArgumentException("Maximum 10ks of this pizza allowed in cart.");
        }

        CartItem item = found.orElse(new CartItem());
        item.setUser(user);
        item.setPizzaItem(pizzaItem);
        item.setQuantity(toSet);

        cartItemRepo.save(item);
    }

    public void removeFromCart(User user, Integer cartItemId) {
        cartItemRepo.deleteById(cartItemId);
    }

}