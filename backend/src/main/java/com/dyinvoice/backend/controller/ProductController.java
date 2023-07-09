package com.dyinvoice.backend.controller;


import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.exception.ValidationException;
import com.dyinvoice.backend.model.entity.Client;
import com.dyinvoice.backend.model.entity.EntitiesRoleName;
import com.dyinvoice.backend.model.entity.Product;
import com.dyinvoice.backend.model.form.ClientForm;
import com.dyinvoice.backend.model.form.ProductForm;
import com.dyinvoice.backend.model.view.AppUserView;
import com.dyinvoice.backend.model.view.ClientView;
import com.dyinvoice.backend.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value="ProductController", description="Rest API for product operations.")
@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping(value = "/v1/user")
public class ProductController {

    private ProductService productService;

    @ApiOperation(value = "Register Product.", response = AppUserView.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Validation Exception"),
            @ApiResponse(code = 404, message = "Resource Not Found Exception"),
            @ApiResponse(code = 500, message = "Internal Exception")

    })
    @PostMapping("{appUserId}/product/createProduct")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductForm form,
                                                 Authentication authentication,
                                                 @PathVariable("appUserId") final String appUserId
    ) throws ValidationException, ResourceNotFoundException {


        boolean useId = false;
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals( EntitiesRoleName.ROLE_ADMIN)
                        || a.getAuthority().equals(EntitiesRoleName.ROLE_STAFF) )) {
            useId = true;
        }

        if(useId){
            form.setId(Long.parseLong(appUserId));

        } else {

            form.setId(Long.parseLong(authentication.getName()));
        }
        Product createdProduct = productService.createProduct(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);

    }



    @ApiOperation(value = "Get all Product.", response = AppUserView.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Exception")
    })
    @GetMapping("{appUserId}/products")
    public List<Product> getAllProducts(Authentication authentication,
                                             @PathVariable("appUserId") final String appUserId) {

        ProductForm form = new ProductForm();

        boolean useId = false;
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals( EntitiesRoleName.ROLE_ADMIN)
                        || a.getAuthority().equals(EntitiesRoleName.ROLE_STAFF) )) {
            useId = true;
        }

        if(useId){

            form.setId(Long.parseLong(appUserId));
        } else {

            form.setId(Long.parseLong(authentication.getName()));
        }

        return productService.getAllProduct();
    }



    @ApiOperation(value = "Delete Product by id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Resource Not Found Exception"),
            @ApiResponse(code = 500, message = "Internal Exception")
    })
    @DeleteMapping("{appUserId}/product/{id}")
    public void deleteClient(@PathVariable long id,
                             Authentication authentication,
                             @PathVariable("appUserId") final String appUserId) {

        ProductForm form = new ProductForm();

        boolean useId = false;
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals( EntitiesRoleName.ROLE_ADMIN)
                        || a.getAuthority().equals(EntitiesRoleName.ROLE_STAFF) )) {
            useId = true;
        }

        if(useId){

            form.setId(Long.parseLong(appUserId));

        } else {

            form.setId(Long.parseLong(authentication.getName()));
        }
        productService.deleteProduct(id);
    }



    @ApiOperation(value = "Get Product by ID or Name.", response = ClientView.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Resource Not Found Exception"),
            @ApiResponse(code = 500, message = "Internal Exception")
    })
    @GetMapping("{appUserId}/product/{idOrName}")
    public ResponseEntity<Product> getProductByIdOrName(@PathVariable("idOrName") final String idOrName,
                                                Authentication authentication,
                                                @PathVariable("appUserId") final String appUserId) throws ResourceNotFoundException {
        ProductForm form = new ProductForm();

        boolean useId = false;
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals( EntitiesRoleName.ROLE_ADMIN)
                        || a.getAuthority().equals(EntitiesRoleName.ROLE_STAFF) )) {
            useId = true;
        }

        if (useId) {

                form.setId(Long.parseLong(appUserId));

        } else {
                form.setId(Long.parseLong(authentication.getName()));
        }

        Product product;
        try {
            // Try as an ID first
            long id = Long.parseLong(idOrName);
            product = productService.getProductById(id);
        } catch (NumberFormatException e) {
            // If it's not a number, treat it as an email
            product = productService.getProductByName(idOrName);
        }

        if (product == null) {
            throw new ResourceNotFoundException("Product not found.");
        }
        return ResponseEntity.ok(product);
    }
}
