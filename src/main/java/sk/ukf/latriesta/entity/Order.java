package sk.ukf.latriesta.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints. DecimalMin;
import jakarta. validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate. annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Používateľ je povinný")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    @NotNull(message = "Status je povinný")
    @Column(nullable = false, length = 45)
    private String status = "pending";

    @Column(columnDefinition = "TEXT")
    private String note;

    @DecimalMin(value = "0.0", message = "Celková cena musí byť aspoň 0")
    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice = BigDecimal.ZERO;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType. LAZY)
    @JoinColumn(name = "chef_id")
    private User chef;

    @ManyToOne(fetch = FetchType. LAZY)
    @JoinColumn(name = "courier_id")
    private User courier;

    @OneToMany(mappedBy = "order", cascade = CascadeType. ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems;

    public Order() {}

    public Order(Integer id, User user, String status, String note,
                 BigDecimal totalPrice, LocalDateTime createdAt, LocalDateTime updatedAt,
                 User chef, User courier, Set<OrderItem> orderItems) {
        this.id = id;
        this.user = user;
        this.status = status;
        this.note = note;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.chef = chef;
        this.courier = courier;
        this.orderItems = orderItems;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getChef() {
        return chef;
    }

    public void setChef(User chef) {
        this.chef = chef;
    }

    public User getCourier() {
        return courier;
    }

    public void setCourier(User courier) {
        this.courier = courier;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}