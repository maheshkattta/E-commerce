package mahi.labs.monkeystore.Repositories;


import mahi.labs.monkeystore.Entities.OrderTable;
import org.springframework.data.jpa.repository.JpaRepository;

// OrderRepository.java
public interface OrderRepository extends JpaRepository<OrderTable, Long> {
}