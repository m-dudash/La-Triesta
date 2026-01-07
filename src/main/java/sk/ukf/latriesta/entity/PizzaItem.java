package sk.ukf.latriesta.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "pizza_item")
public class PizzaItem {

    @Id
    @GeneratedValue(strategy = GenerationType. IDENTITY)
    private Integer id;

    @NotNull(message = "Pizza je povinná")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pizzas_id", nullable = false)
    private Pizza pizza;

    @NotNull(message = "Veľkosť je povinná")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sizes_id", nullable = false)
    private Size size;

    @NotNull(message = "Cena je povinná")
    @DecimalMin(value = "0.0", inclusive = false, message = "Cena musí byť väčšia ako 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "pizzaItem")
    private Set<CartItem> cartItems;

    @OneToMany(mappedBy = "pizzaItem")
    private Set<OrderItem> orderItems;

    public PizzaItem() {}

    public PizzaItem(Integer id, Pizza pizza, Size size, BigDecimal price, Boolean active,
                     Set<CartItem> cartItems, Set<OrderItem> orderItems) {
        this.id = id;
        this.pizza = pizza;
        this.size = size;
        this.price = price;
        this.active = active;
        this.cartItems = cartItems;
        this.orderItems = orderItems;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}