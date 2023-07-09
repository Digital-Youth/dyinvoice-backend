package com.dyinvoice.backend.service.implementation;


import com.dyinvoice.backend.dao.ProductDAO;
import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.exception.ValidationException;
import com.dyinvoice.backend.model.entity.Entreprise;
import com.dyinvoice.backend.model.entity.Prestation;
import com.dyinvoice.backend.model.entity.Product;
import com.dyinvoice.backend.model.form.ProductForm;
import com.dyinvoice.backend.model.validator.FormValidator;
import com.dyinvoice.backend.repository.EntrepriseRepository;
import com.dyinvoice.backend.service.ProductService;
import com.dyinvoice.backend.utils.FormToEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;

    private final EntrepriseRepository entrepriseRepository;


    @Autowired
    public ProductServiceImpl(ProductDAO productDAO, EntrepriseRepository entrepriseRepository) {
        this.productDAO = productDAO;
        this.entrepriseRepository = entrepriseRepository;
    }

    @Override
    public Product createProduct(ProductForm form) throws ValidationException, ResourceNotFoundException {

        List<String> errorList = FormValidator.validateProductForm(form);
        if (errorList.size() > 0) {
            throw new ValidationException(FormValidator.getErrorMessages(errorList));
        }

        Entreprise entreprise = entrepriseRepository.findById(form.getEntrepriseId())
                .orElseThrow(() -> new ResourceNotFoundException("Entreprise not found with id " + form.getEntrepriseId()));

        Product product = FormToEntityConverter.convertProductFormToProduct(form);

        product.setEntreprise(entreprise);

        productDAO.createProduct(product);

        return product;
    }

    @Override
    public List<Product> getAllProduct() {
        return productDAO.getAllProducts();
    }

    @Override
    public Product getProductByName(String name) throws ResourceNotFoundException {

        Product product = productDAO.getProductByName(name);

        if(product == null) {
            throw new ResourceNotFoundException("Product not found with name " + product);
        }
        return product;
    }

    @Override
    public Product updateProduct(Product product) throws ValidationException, ResourceNotFoundException {
        return null;
    }

    @Override
    public void deleteProduct(long id) {

        productDAO.deleteProduct(id);
    }

    @Override
    public Product getProductById(long id) throws ResourceNotFoundException {
        Product product = productDAO.getProductById(id);
        if (product == null) {
            throw new ResourceNotFoundException("Product not found with id " + id);
        }
        return product;
    }
}
