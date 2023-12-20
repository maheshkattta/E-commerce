package mahi.labs.monkeystore.Repositories;

import mahi.labs.monkeystore.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// ModelRepository.java
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByCategoryName(String catogoryName);

}