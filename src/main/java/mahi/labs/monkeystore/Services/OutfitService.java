package mahi.labs.monkeystore.Services;

import mahi.labs.monkeystore.Entities.*;
import mahi.labs.monkeystore.Repositories.*;
import mahi.labs.monkeystore.Repositories.ModelRepository;
import mahi.labs.monkeystore.Repositories.OutfitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutfitService {

    private static final Logger logger = LoggerFactory.getLogger(OutfitService.class);

    @Autowired
    private OutfitRepository outfitRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Outfit> getOutfitsByCategoryId(Long categoryId) {
        try {
            logger.info("Getting outfits by category ID: {}", categoryId);
            return outfitRepository.findByCategoryCategoryId(categoryId);
        } catch (Exception e) {
            logger.error("Error getting outfits by category ID: {}", e.getMessage());
            throw e;
        }
    }

    public List<Outfit> getOutfitsByCategoryIdAndModelId(Long categoryId, Long modelId) {
        try {
            logger.info("Getting outfits by category ID: {} and model ID: {}", categoryId, modelId);
            return outfitRepository.findByCategoryCategoryIdAndModelModelId(categoryId, modelId);
        } catch (Exception e) {
            logger.error("Error getting outfits by category ID and model ID: {}", e.getMessage());
            throw e;
        }
    }

    public List<Outfit> getOutfitsByModelName(String modelName) {
        try {
            logger.info("Getting outfits by model name: {}", modelName);
            return outfitRepository.findByModelModelName(modelName);
        } catch (Exception e) {
            logger.error("Error getting outfits by model name: {}", e.getMessage());
            throw e;
        }
    }

    public Outfit getOutfitById(Long productId) {
        try {
            logger.info("Getting outfit by ID: {}", productId);
            return outfitRepository.findById(productId).orElse(null);
        } catch (Exception e) {
            logger.error("Error getting outfit by ID: {}", e.getMessage());
            throw e;
        }
    }

    public List<Brand> getBrandsByBrandName(String brandName) {
        try {
            logger.info("Getting brands by brand name: {}", brandName);
            return brandRepository.findByBrandName(brandName);
        } catch (Exception e) {
            logger.error("Error getting brands by brand name: {}", e.getMessage());
            throw e;
        }
    }

    public List<Category> getCategoriesByCategoryName(String categoryName) {
        try {
            logger.info("Getting categories by category name: {}", categoryName);
            return categoryRepository.findByCategoryName(categoryName);
        } catch (Exception e) {
            logger.error("Error getting categories by category name: {}", e.getMessage());
            throw e;
        }
    }

    public List<Model> getAllModels() {
        try {
            logger.info("Getting all models");
            return modelRepository.findAll();
        } catch (Exception e) {
            logger.error("Error getting all models: {}", e.getMessage());
            throw e;
        }
    }

    public List<Brand> getAllBrands() {
        try {
            logger.info("Getting all brands");
            return brandRepository.findAll();
        } catch (Exception e) {
            logger.error("Error getting all brands: {}", e.getMessage());
            throw e;
        }
    }

    public List<Category> getAllCategories() {
        try {
            logger.info("Getting all categories");
            return categoryRepository.findAll();
        } catch (Exception e) {
            logger.error("Error getting all categories: {}", e.getMessage());
            throw e;
        }
    }

    public List<Outfit> finaloutputs(String categoryName, String modelName, String brandName, String colorName, Double minPrice, Double maxPrice) {
        try {
            logger.info("Applying filters to get outfits");

            Specification<Outfit> specification = Specification.where(null);

            if (categoryName != null) {
                specification = specification.and((root, query, builder) ->
                        builder.equal(root.join("category").get("categoryName"), categoryName));
            }

            if (modelName != null) {
                specification = specification.and((root, query, builder) ->
                        builder.equal(root.join("model").get("modelName"), modelName));
            }

            if (brandName != null) {
                specification = specification.and((root, query, builder) ->
                        builder.equal(root.join("brand").get("brandName"), brandName));
            }

            if (colorName != null) {
                specification = specification.and((root, query, builder) ->
                        builder.equal(root.join("color").get("colorName"), colorName));
            }

            if (minPrice != null) {
                if (maxPrice != null) {
                    specification = specification.and((root, query, builder) ->
                            builder.between(root.get("price"), minPrice, maxPrice));
                } else {
                    specification = specification.and((root, query, builder) ->
                            builder.greaterThanOrEqualTo(root.get("price"), minPrice));
                }
            }

            return outfitRepository.findAll(specification);
        } catch (Exception e) {
            logger.error("Error applying filters to get outfits: {}", e.getMessage());
            throw e;
        }
    }
}
