package mahi.labs.monkeystore.Repositories;

import mahi.labs.monkeystore.Entities.Model;
import org.springframework.data.jpa.repository.JpaRepository;

// ModelRepository.java
public interface ModelRepository extends JpaRepository<Model, Long> {
}
