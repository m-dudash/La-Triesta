package sk.ukf.latriesta.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType. IDENTITY)
    private Integer id;

    @NotBlank(message = "Názov prísady je povinný")
    @Column(nullable = false, length = 45)
    private String name;

    @ManyToMany(mappedBy = "ingredients")
    private Set<Pizza> pizzas;

    public Ingredient() {}

    public Ingredient(Integer id, String name, Set<Pizza> pizzas) {
        this.id = id;
        this.name = name;
        this.pizzas = pizzas;
    }

    public Integer getId() {
        return id;
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

    public Set<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(Set<Pizza> pizzas) {
        this.pizzas = pizzas;
    }
}