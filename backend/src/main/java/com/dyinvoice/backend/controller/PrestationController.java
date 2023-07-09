package com.dyinvoice.backend.controller;

import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.exception.ValidationException;
import com.dyinvoice.backend.model.entity.EntitiesRoleName;
import com.dyinvoice.backend.model.entity.Prestation;
import com.dyinvoice.backend.model.form.PrestationForm;
import com.dyinvoice.backend.model.view.AppUserView;
import com.dyinvoice.backend.service.PrestationService;
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



@Api(value="PrestationController", description="Rest API for prestation operations.")
@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping(value = "/v1/user")
public class PrestationController {

    private PrestationService prestationService;

    @ApiOperation(value = "Register Prestation.", response = AppUserView.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Validation Exception"),
            @ApiResponse(code = 404, message = "Resource Not Found Exception"),
            @ApiResponse(code = 500, message = "Internal Exception")

    })
    @PostMapping("{appUserId}/prestation/createPrestation")
    public ResponseEntity<Prestation> createClient(@Valid @RequestBody PrestationForm form,
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
        Prestation createdPrestation = prestationService.createPrestation(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPrestation);

    }



    @ApiOperation(value = "Get all Prestations.", response = AppUserView.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Exception")
    })
    @GetMapping("{appUserId}/prestations")
    public List<Prestation> getAllPrestation(Authentication authentication,
                                                      @PathVariable("appUserId") final String appUserId) {

        PrestationForm form = new PrestationForm();

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

        return prestationService.getAllPrestation();
    }



    @ApiOperation(value = "Delete Prestation by id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Resource Not Found Exception"),
            @ApiResponse(code = 500, message = "Internal Exception")
    })
    @DeleteMapping("{appUserId}/prestation/{id}")
    public void deleteClient(@PathVariable long id,
                                             Authentication authentication,
                                             @PathVariable("appUserId") final String appUserId) {

        PrestationForm form = new PrestationForm();

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
        prestationService.deletePrestation(id);
    }



    @ApiOperation(value = "Get Prestation by name.", response = AppUserView.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Resource Not Found Exception"),
            @ApiResponse(code = 500, message = "Internal Exception")
    })
    @GetMapping("{appUserId}/prestation/{name}")
    public ResponseEntity<Prestation> getPrestationByName(@PathVariable("name") final String name,
                                                Authentication authentication,
                                                @PathVariable("appUserId") final String appUserId) throws ResourceNotFoundException {
        PrestationForm form = new PrestationForm();

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

        Prestation prestation;
        prestation = prestationService.getPrestationByName(name);



        if (prestation == null) {
            throw new ResourceNotFoundException("Prestation not found.");
        }
        return ResponseEntity.ok(prestation);
    }

}
