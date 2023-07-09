package com.dyinvoice.backend.service;


import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.exception.ValidationException;
import com.dyinvoice.backend.model.entity.Client;
import com.dyinvoice.backend.model.entity.Product;
import com.dyinvoice.backend.model.form.ProductForm;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductForm form) throws ValidationException, ResourceNotFoundException;

    List<Product> getAllProduct();

    Product getProductByName(String name) throws ResourceNotFoundException;
    Product updateProduct(Product product) throws ValidationException, ResourceNotFoundException;
    void deleteProduct(long id);

    Product getProductById(long id) throws ResourceNotFoundException;
}
