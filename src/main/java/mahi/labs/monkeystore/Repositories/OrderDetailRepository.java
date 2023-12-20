package mahi.labs.monkeystore.Repositories;

import mahi.labs.monkeystore.Entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

// OrderDetailRepository.java
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}