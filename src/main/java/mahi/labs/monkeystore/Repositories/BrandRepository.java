package mahi.labs.monkeystore.Repositories;

import mahi.labs.monkeystore.Entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// ModelRepository.java
public interface BrandRepository extends JpaRepository<Brand, Long> {
    List<Brand> findByBrandName(String brandName);

}
