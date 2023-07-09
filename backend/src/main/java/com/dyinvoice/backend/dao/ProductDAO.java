package com.dyinvoice.backend.dao;

import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.exception.ValidationException;
import com.dyinvoice.backend.model.entity.Client;
import com.dyinvoice.backend.model.entity.Product;

import java.util.List;

public interface ProductDAO {

    Product createProduct(Product product) throws ValidationException, ResourceNotFoundException;

    List<Product> getAllProducts();

    Product updateProduct(Product product);

    void deleteProduct(long id);

    Product getProductByName(String name);

    Product getProductById(long id);
}
