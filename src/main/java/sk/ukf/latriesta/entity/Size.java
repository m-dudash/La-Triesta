package sk.ukf.latriesta.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "sizes")
public class Size {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Názov veľkosti je povinný")
    @Column(nullable = false, length = 45)
    private String name;

    @Positive(message = "Priemer musí byť kladné číslo")
    @Column(precision = 5, scale = 2)
    private BigDecimal diameter;

    @Positive(message = "Hmotnosť musí byť kladné číslo")
    private Integer weight;

    @Column(nullable = false)
    private Boolean deleted = false; // soft-delete

    @OneToMany(mappedBy = "size")
    private Set<PizzaItem> pizzaItems;

    public Size() {}

    public Size(Integer id, String name, BigDecimal diameter, Integer weight, Boolean deleted, Set<PizzaItem> pizzaItems) {
        this.id = id;
        this.name = name;
        this.diameter = diameter;
        this.weight = weight;
        this.deleted = deleted;
        this.pizzaItems = pizzaItems;
    }

    public Integer getId() {
        return id;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getDiameter() {
        return diameter;
    }

    public void setDiameter(BigDecimal diameter) {
        this.diameter = diameter;
    }

    public Boolean getDeleted() { return deleted; }
    public void setDeleted(Boolean deleted) { this.deleted = deleted; }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Set<PizzaItem> getPizzaItems() {
        return pizzaItems;
    }

    public void setPizzaItems(Set<PizzaItem> pizzaItems) {
        this.pizzaItems = pizzaItems;
    }
}