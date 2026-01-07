package sk.ukf.latriesta.service.pizza;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.ukf.latriesta.entity.Ingredient;
import sk.ukf.latriesta.entity.Pizza;
import sk.ukf.latriesta.repository.IngredientRepository;
import sk.ukf.latriesta.repository.PizzaRepository;
import sk.ukf.latriesta.repository.TagRepository;

import java.text.Normalizer;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {

    private final PizzaRepository pizzaRepository;
    private final IngredientRepository ingredientRepository;
    private final TagRepository tagRepository;

    @Autowired
    public PizzaService(PizzaRepository pizzaRepository,
                        IngredientRepository ingredientRepository,
                        TagRepository tagRepository) {
        this.pizzaRepository = pizzaRepository;
        this.ingredientRepository = ingredientRepository;
        this.tagRepository = tagRepository;
    }

    public List<Pizza> findAll() {
        return pizzaRepository.findByDeletedFalse();
    }

    public Optional<Pizza> findById(Integer id) {
        return pizzaRepository.findById(id).filter(p -> !Boolean.TRUE.equals(p.getDeleted()));
    }

    public Optional<Pizza> findBySlug(String slug) {
        return pizzaRepository.findBySlug(slug).filter(p -> !Boolean.TRUE.equals(p.getDeleted()));
    }

    public List<Pizza> findAvailable() {
        return pizzaRepository.findByAvailableTrueAndDeletedFalse();
    }

    public List<Pizza> findByTagName(String tagName) {
        return pizzaRepository.findByTagName(tagName);
    }

    public List<Pizza> searchByKeyword(String keyword) {
        return pizzaRepository.searchByKeyword(keyword);
    }

    // hlavna save metoda
    @Transactional
    public Pizza save(Pizza pizza, List<Integer> ingredientIds, List<Integer> tagIds) {

        pizza.setIngredients(
                ingredientIds == null || ingredientIds.isEmpty()
                        ? new HashSet<>()
                        : new HashSet<>(ingredientRepository.findAllById(ingredientIds))
        );

        pizza.setTags(
                tagIds == null || tagIds.isEmpty()
                        ? new HashSet<>()
                        : new HashSet<>(tagRepository.findAllById(tagIds))
        );

        if (pizza.getSlug() == null || pizza.getSlug().trim().isEmpty()) {
            String name = pizza.getName();
            if (name == null || name.trim().isEmpty()) name = "pizza";
            pizza.setSlug(generateSlug(name));
        }

        if (pizza.getDeleted() == null) {
            pizza.setDeleted(false);
        }

        if (pizza.getId() == null) {
            long activeItemsCount = pizza.getPizzaItems().stream()
                    .filter(item -> item != null && item.getActive() != null && item.getActive())
                    .count();
            if (activeItemsCount < 1) {
                throw new IllegalArgumentException("Musíte pridať aspoň jednu veľkosť s cenou.");
            }
        }

        return pizzaRepository.save(pizza);
    }

    // jednoduchejsia save metoda bez ingredientov a tagov
    @Transactional
    public Pizza save(Pizza pizza) {
        if (pizza.getSlug() == null || pizza.getSlug().trim().isEmpty()) {
            String name = pizza.getName();
            if (name == null || name.trim().isEmpty()) {
                name = "pizza";
            }
            pizza.setSlug(generateSlug(name));
        }
        if (pizza.getDeleted() == null) {
            pizza.setDeleted(false);
        }
        return pizzaRepository.save(pizza);
    }

    // soft-delete
    public void deleteById(Integer id) {
        Pizza pizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pizza nenájdená"));
        pizza.setDeleted(true);
        pizza.setAvailable(false);
        pizzaRepository.save(pizza);
    }

    private String generateSlug(String name) {
        String normalized = Normalizer.normalize(name.trim(), Normalizer.Form.NFD);
        String slugBase = normalized.replaceAll("\\p{M}", "") // удаляем диакритику
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "") // оставляем только буквы, цифры, пробелы и дефисы
                .trim()
                .replaceAll("\\s+", "-"); // пробелы → дефисы

        if (slugBase.isEmpty()) {
            slugBase = "pizza";
        }

        String candidate = slugBase;
        int counter = 1;
        while (pizzaRepository.findBySlug(candidate).isPresent()) {
            candidate = slugBase + "-" + counter;
            counter++;
        }

        return candidate;
    }

}