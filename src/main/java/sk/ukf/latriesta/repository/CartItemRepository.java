package sk.ukf.latriesta.repository;

import sk.ukf.latriesta.entity.CartItem;
import org. springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findByUserId(Integer userId);

    Optional<CartItem> findByUserIdAndPizzaItemId(Integer userId, Integer pizzaItemId);

    void deleteByUserId(Integer userId);
}