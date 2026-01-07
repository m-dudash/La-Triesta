package sk.ukf.latriesta.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.ukf.latriesta.entity.Order;
import sk.ukf.latriesta.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepo;

    @Autowired
    public OrderService(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    public List<Order> findAll() {
        return orderRepo.findAll();
    }

    public Page<Order> findAll(int page, int size) {
        return orderRepo.findAll(PageRequest.of(page, size));
    }

    public Optional<Order> findById(Integer id) {
        return orderRepo.findById(id);
    }

    public Order save(Order order) {
        return orderRepo.save(order);
    }

    public void deleteById(Integer id) {
        orderRepo.deleteById(id);
    }

    public Long countPendingOrders() {
        Long count = orderRepo.countPendingOrders();
        return count != null ? count : 0L;
    }

    public List<Order> userOrdersLastWeek(Integer userId) {
        return orderRepo.findUserOrdersLastWeek(userId, LocalDateTime.now().minusDays(7));
    }

    public List<Order> userOrdersLastMonth(Integer userId) {
        return orderRepo.findUserOrdersLastMonth(userId, LocalDateTime.now().minusMonths(1));
    }

    public List<Order> userOrdersLastYear(Integer userId) {
        return orderRepo.findUserOrdersLastYear(userId, LocalDateTime.now().minusYears(1));
    }

    public Double userTotalSpent(Integer userId) {
        return orderRepo.sumUserOrdersTotal(userId);
    }
}