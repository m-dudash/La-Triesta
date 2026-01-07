package sk.ukf.latriesta.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pizzas")
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Názov pizze je povinný")
    @Size(max = 100, message = "Názov môže mať maximálne 100 znakov")
    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(nullable = false)
    private Boolean available = true;

    @Column(nullable = false)
    private Boolean deleted = false; // soft-delete

    @Size(max = 100, message = "Slug môže mať maximálne 100 znakov")
    @Column(nullable = false, unique = true, length = 100)
    private String slug;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            name = "pizza_ingredients",
            joinColumns = @JoinColumn(name = "pizzas_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredients_id")
    )
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "pizza_tags",
            joinColumns = @JoinColumn(name = "pizzas_id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "pizza",
            cascade = CascadeType.ALL)
    private Set<PizzaItem> pizzaItems = new HashSet<>();



    public Pizza() {}

    public Pizza(Integer id, String name, String description, String imageUrl,
                 Boolean available, Boolean deleted, String slug, LocalDateTime createdAt,
                 LocalDateTime updatedAt, Set<Ingredient> ingredients,
                 Set<Tag> tags, Set<PizzaItem> pizzaItems) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.available = available;
        this.deleted = deleted;
        this.slug = slug;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.ingredients = ingredients;
        this.tags = tags;
        this.pizzaItems = pizzaItems;
    }

    public void addPizzaItem(PizzaItem item) {
        pizzaItems.add(item);
        item.setPizza(this);
    }


    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }

    public Boolean getDeleted() { return deleted; }
    public void setDeleted(Boolean deleted) { this.deleted = deleted; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Set<Ingredient> getIngredients() { return ingredients; }
    public void setIngredients(Set<Ingredient> ingredients) { this.ingredients = ingredients; }

    public Set<Tag> getTags() { return tags; }
    public void setTags(Set<Tag> tags) { this.tags = tags; }

    public Set<PizzaItem> getPizzaItems() { return pizzaItems; }
    public void setPizzaItems(Set<PizzaItem> pizzaItems) { this.pizzaItems = pizzaItems; }
}