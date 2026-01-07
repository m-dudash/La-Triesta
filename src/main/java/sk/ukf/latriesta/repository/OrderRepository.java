package sk.ukf.latriesta.repository;

import sk.ukf.latriesta.entity.Order;
import org.springframework.data. jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data. repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util. List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUserId(Integer userId);


    // custom metody x5
    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = 'pending'")
    Long countPendingOrders();

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.createdAt >= :weekAgo ORDER BY o.createdAt DESC")
    List<Order> findUserOrdersLastWeek(@Param("userId") Integer userId,
                                       @Param("weekAgo") LocalDateTime weekAgo);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.createdAt >= :monthAgo ORDER BY o.createdAt DESC")
    List<Order> findUserOrdersLastMonth(@Param("userId") Integer userId,
                                        @Param("monthAgo") LocalDateTime monthAgo);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.createdAt >= :yearAgo ORDER BY o.createdAt DESC")
    List<Order> findUserOrdersLastYear(@Param("userId") Integer userId,
                                       @Param("yearAgo") LocalDateTime yearAgo);

    @Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o WHERE o.user.id = :userId AND o.status NOT IN ('cancelled')")
    Double sumUserOrdersTotal(@Param("userId") Integer userId);

    List<Order> findByStatusInOrderByCreatedAtAsc(List<String> statuses);
}