package mahi.labs.monkeystore.Repositories;

import mahi.labs.monkeystore.Entities.Outfit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutfitRepository extends JpaRepository<Outfit, Long> {

    Logger logger = LoggerFactory.getLogger(OutfitRepository.class);

    List<Outfit> findByModelModelName(String modelName);
    List<Outfit> findByCategoryCategoryId(Long categoryId);
    List<Outfit> findByCategoryCategoryIdAndModelModelId(Long categoryId, Long modelId);

    List<Outfit> findAll(Specification<Outfit> specification);

    default List<Outfit> findAllWithExceptionHandling(Specification<Outfit> specification) {
        try {
            return findAll(specification);
        } catch (Exception e) {
            logger.error("Error while retrieving outfits: {}", e.getMessage());
            throw e; // Re-throw the exception after logging
        }
    }
}
