package sk.ukf.latriesta.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints. Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations. UpdateTimestamp;

import java. time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roles_id", nullable = false)
    private Role role;

    @NotBlank(message = "Meno používateľa je povinné")
    @Size(min = 3, max = 100, message = "Meno používateľa musí mať 3-100 znakov")
    @Column(nullable = false, length = 100)
    private String username;

    @NotBlank(message = "Email je povinný")
    @Email(message = "Neplatný formát emailu")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "Heslo je povinné")
    @Size(min = 8, message = "Heslo musí mať aspoň 8 znakov")
    @Column(nullable = false, length = 255)
    private String password;

    @NotBlank(message = "Adresa je povinná")
    @Column(nullable = false, length = 255)
    private String address;

    @Column(name = "avatar_url", length = 255)
    private String avatarUrl;

    @NotBlank(message = "Telefón je povinný")
    @Pattern(
            regexp = "^\\+?[0-9 ]{9,20}$",
            message = "Neplatný formát telefónneho čísla"
    )
    @Column(nullable = false, unique = true, length = 20)
    private String phone;


    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;

    public User() {}

    public User(Integer id, Role role, String username, String email, String password,
                String address, String phone, LocalDateTime createdAt,
                LocalDateTime updatedAt, Set<CartItem> cartItems, Set<Order> orders) {
        this.id = id;
        this. role = role;
        this. username = username;
        this. email = email;
        this. password = password;
        this. address = address;
        this. phone = phone;
        this.createdAt = createdAt;
        this. updatedAt = updatedAt;
        this.cartItems = cartItems;
        this.orders = orders;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}