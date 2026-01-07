package sk.ukf.latriesta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.ukf.latriesta.entity.PizzaItem;

import java.util.List;

@Repository
public interface PizzaItemRepository extends JpaRepository<PizzaItem, Integer> {

    List<PizzaItem> findByPizzaId(Integer pizzaId);

    List<PizzaItem> findByPizzaIdAndActiveTrue(Integer pizzaId);



}