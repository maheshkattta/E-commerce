package mahi.labs.monkeystore.Controllers;

import mahi.labs.monkeystore.Entities.*;
import mahi.labs.monkeystore.Services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Configuration
@RestController
@RequestMapping("/api")
public class OutfitController {

    private static final Logger logger = LoggerFactory.getLogger(OutfitController.class);

    @Autowired
    private OutfitService outfitService;

    @GetMapping("/getbyid")
    public ResponseEntity<Outfit> getByProductId(@RequestParam Long id) {
        try {
            Outfit outfit = outfitService.getOutfitById(id);
            return new ResponseEntity<>(outfit, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error getting outfit by ID: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/allModels")
    public ResponseEntity<List<Model>> getAllModels() {
        try {
            List<Model> models = outfitService.getAllModels();
            return new ResponseEntity<>(models, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error getting all models: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/allBrands")
    public ResponseEntity<List<Brand>> getAllBrands() {
        try {
            List<Brand> brands = outfitService.getAllBrands();
            return new ResponseEntity<>(brands, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error getting all brands: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/allCategories")
    public ResponseEntity<List<Category>> getAllCategories() {
        try {
            List<Category> categories = outfitService.getAllCategories();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error getting all categories: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/user-request")
    public ResponseEntity<List<Outfit>> filterOutfitsByMultipleCriteria(
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String modelName,
            @RequestParam(required = false) String brandName,
            @RequestParam(required = false) String colorName,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        try {
            logger.info("Filtering outfits by: Category={}, Model={}, Brand={}, Color={}, MinPrice={}, MaxPrice={}",
                    categoryName, modelName, brandName, colorName, minPrice, maxPrice);

            List<Outfit> filteredOutfits = outfitService.finaloutputs(categoryName, modelName, brandName, colorName, minPrice, maxPrice);
            return ResponseEntity.ok(filteredOutfits);
        } catch (Exception e) {
            logger.error("Error filtering outfits: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
