package com.dyinvoice.backend.dao.implementation;

import com.dyinvoice.backend.dao.ProductDAO;
import com.dyinvoice.backend.exception.ValidationException;
import com.dyinvoice.backend.model.entity.Entreprise;
import com.dyinvoice.backend.model.entity.Product;
import com.dyinvoice.backend.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class ProductDAOImpl implements ProductDAO {

    private static final Logger logger = LoggerFactory.getLogger(PrestationDAOImpl.class);

    private final ProductRepository productRepository;


    @Autowired
    public ProductDAOImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public Product createProduct(Product product) throws ValidationException {
        logger.info("Check if the product exists");

        Entreprise entreprise = product.getEntreprise();
        Optional<Product> existingProduct = productRepository.findByNameAndEntreprise(product.getName(), entreprise);

        if (existingProduct.isPresent()) {
            logger.error("Product already exists for the given enterprise");
            throw new ValidationException("Product already exists for this enterprise");
        }

        logger.info("Saving product to database...");
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(Product product) {
        return null;
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductByName(String name) {
        Optional<Product> product = productRepository.findByName(name);
        return product.orElse(null);
    }

    @Override
    public Product getProductById(long id) {
        Optional<Product> product = productRepository.findById(id);

        return product.orElse(null);
    }
}
