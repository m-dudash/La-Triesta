package sk.ukf.latriesta.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Používateľ je povinný")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    @NotNull(message = "Položka pizze je povinná")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pizza_item_id", nullable = false)
    private PizzaItem pizzaItem;

    @Min(value = 1, message = "Množstvo musí byť aspoň 1")
    @Column(nullable = false)
    private Integer quantity = 1;

    public CartItem() {}

    public CartItem(Integer id, User user, PizzaItem pizzaItem, Integer quantity) {
        this.id = id;
        this.user = user;
        this.pizzaItem = pizzaItem;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PizzaItem getPizzaItem() {
        return pizzaItem;
    }

    public void setPizzaItem(PizzaItem pizzaItem) {
        this.pizzaItem = pizzaItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}