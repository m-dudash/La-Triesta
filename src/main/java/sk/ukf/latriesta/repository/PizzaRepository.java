package sk.ukf.latriesta.repository;

import sk.ukf.latriesta.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

    Optional<Pizza> findBySlug(String slug);

    List<Pizza> findByAvailableTrueAndDeletedFalse();

    List<Pizza> findByDeletedFalse();

    // custom metody x2
    
    @Query("SELECT DISTINCT p FROM Pizza p " +
            "LEFT JOIN p.tags t " +
            "WHERE p.deleted = false AND p.available = true " +
            "AND (LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Pizza> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT p FROM Pizza p JOIN p.tags t WHERE p.deleted = false AND p.available = true AND t.name = :tagName")
    List<Pizza> findByTagName(@Param("tagName") String tagName);
}