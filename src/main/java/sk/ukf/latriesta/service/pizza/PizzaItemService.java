package sk.ukf.latriesta.service.pizza;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.ukf.latriesta.entity.PizzaItem;
import sk.ukf.latriesta.repository.PizzaItemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaItemService {

    private final PizzaItemRepository pizzaItemRepository;

    @Autowired
    public PizzaItemService(PizzaItemRepository pizzaItemRepository) {
        this.pizzaItemRepository = pizzaItemRepository;
    }

    public List<PizzaItem> findAll() {
        return pizzaItemRepository.findAll();
    }

    public Optional<PizzaItem> findById(Integer id) {
        return pizzaItemRepository.findById(id);
    }

    public List<PizzaItem> findByPizzaId(Integer pizzaId) {
        System.out.println("Finding pizzaItems for pizzaId: " + pizzaId);
        List<PizzaItem> items = pizzaItemRepository.findByPizzaId(pizzaId);
        System.out.println("Found " + items.size() + " items");
        return items;
    }

    public List<PizzaItem> findActiveByPizzaId(Integer pizzaId) {
        return pizzaItemRepository.findByPizzaIdAndActiveTrue(pizzaId);
    }

    public PizzaItem save(PizzaItem pizzaItem) {
        System.out.println("Saving PizzaItem: pizzaId=" + pizzaItem.getPizza().getId() + ", sizeId=" + pizzaItem.getSize().getId() + ", price=" + pizzaItem.getPrice() + ", active=" + pizzaItem.getActive());
        PizzaItem saved = pizzaItemRepository.save(pizzaItem);
        System.out.println("Saved PizzaItem id: " + saved.getId());
        return saved;
    }

    public void softDeleteById(Integer id) {
        PizzaItem pizzaItem = pizzaItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PizzaItem nenájdený"));
        pizzaItem.setActive(false);
        pizzaItemRepository.save(pizzaItem);
    }

    public void restoreById(Integer id) {
        PizzaItem pizzaItem = pizzaItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PizzaItem nenájdený"));
        pizzaItem.setActive(true);
        pizzaItemRepository.save(pizzaItem);
    }
}