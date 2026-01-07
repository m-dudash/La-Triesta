package sk.ukf.latriesta.service.pizza;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.ukf.latriesta.entity.Ingredient;
import sk.ukf.latriesta.repository.IngredientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    public Optional<Ingredient> findById(Integer id) {
        return ingredientRepository.findById(id);
    }

    public Ingredient save(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public void deleteById(Integer id) {
        ingredientRepository.deleteById(id);
    }
}