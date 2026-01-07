package sk.ukf.latriesta.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation. constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Objednávka je povinná")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id", nullable = false)
    private Order order;

    @NotNull(message = "Položka pizze je povinná")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pizza_item_id", nullable = false)
    private PizzaItem pizzaItem;

    @NotBlank(message = "Názov položky je povinný")
    @Column(name = "item_name", nullable = false, length = 100)
    private String itemName;

    @NotBlank(message = "Veľkosť položky je povinná")
    @Column(name = "item_size", nullable = false, length = 45)
    private String itemSize;

    @NotNull(message = "Cena položky je povinná")
    @DecimalMin(value = "0.0", inclusive = false, message = "Cena musí byť väčšia ako 0")
    @Column(name = "item_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal itemPrice;

    @Min(value = 1, message = "Množstvo musí byť aspoň 1")
    @Column(nullable = false)
    private Integer quantity = 1;

    public OrderItem() {}

    public OrderItem(Integer id, Order order, PizzaItem pizzaItem,
                     String itemName, String itemSize, BigDecimal itemPrice, Integer quantity) {
        this.id = id;
        this.order = order;
        this.pizzaItem = pizzaItem;
        this.itemName = itemName;
        this.itemSize = itemSize;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public PizzaItem getPizzaItem() {
        return pizzaItem;
    }

    public void setPizzaItem(PizzaItem pizzaItem) {
        this.pizzaItem = pizzaItem;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void createSnapshot(PizzaItem pizzaItem) {
        this.pizzaItem = pizzaItem;
        this.itemName = pizzaItem.getPizza().getName();
        this.itemSize = pizzaItem.getSize().getName();
        this.itemPrice = pizzaItem.getPrice();
    }
}