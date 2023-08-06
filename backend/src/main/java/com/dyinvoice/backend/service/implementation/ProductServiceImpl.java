package com.dyinvoice.backend.service.implementation;


import com.dyinvoice.backend.dao.AppUserDAO;
import com.dyinvoice.backend.dao.ProductDAO;
import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.exception.ValidationException;
import com.dyinvoice.backend.model.entity.Entreprise;
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
    private final AppUserDAO appUserDAO;
    private final EntrepriseRepository entrepriseRepository;


    @Autowired
    public ProductServiceImpl(ProductDAO productDAO, AppUserDAO appUserDAO, EntrepriseRepository entrepriseRepository) {
        this.productDAO = productDAO;
        this.appUserDAO = appUserDAO;
        this.entrepriseRepository = entrepriseRepository;
    }

    public Product createProduct(ProductForm form) throws ValidationException, ResourceNotFoundException {

        List<String> errorList = FormValidator.validateProductForm(form);
        if (errorList.size() > 0) {
            throw new ValidationException(FormValidator.getErrorMessages(errorList));
        }

        Long entrepriseId = appUserDAO.getLoggedInUserEntrepriseId();
        if (entrepriseId == null) {
            throw new ResourceNotFoundException("Entreprise not found for the logged-in user");
        }

        Entreprise entreprise = entrepriseRepository.findById(entrepriseId)
                .orElseThrow(() -> new ResourceNotFoundException("Entreprise not found with id " + entrepriseId));

        Product product = FormToEntityConverter.convertProductFormToProduct(form);
        product.setEntreprise(entreprise);

        return productDAO.createProduct(product);
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
