package com.dyinvoice.backend.controller;


import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.exception.ValidationException;
import com.dyinvoice.backend.model.entity.EntitiesRoleName;
import com.dyinvoice.backend.model.entity.Facture;
import com.dyinvoice.backend.model.enumaration.FactureStatus;
import com.dyinvoice.backend.model.form.FactureForm;
import com.dyinvoice.backend.model.view.ClientView;
import com.dyinvoice.backend.security.JwtTokenProvider;
import com.dyinvoice.backend.service.FactureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Api(value="FactureController", description="Rest API for Facture operations.")
@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping(value = "/v1/user")
public class FactureController {

    private static final Logger logger = LoggerFactory.getLogger(FactureController.class);

    private final FactureService factureService;
    private final JwtTokenProvider jwtTokenProvider;


    @ApiOperation(value = "Register Client.", response = ClientView.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Validation Exception"),
            @ApiResponse(code = 404, message = "Resource Not Found Exception"),
            @ApiResponse(code = 500, message = "Internal Exception")

    })
    @PostMapping("{appUserId}/facture/createFacture")
    public ResponseEntity<Facture> createFacture(@RequestBody FactureForm form, Authentication authentication,
                                                 @PathVariable("appUserId") final String appUserId,
                                                 HttpServletRequest request) throws ValidationException, ResourceNotFoundException{
        String jwtToken = request.getHeader("Authorization").substring(7);
        logger.debug(jwtToken);


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
        try {
            Facture facture = factureService.createFacture(form);
            return ResponseEntity.ok(facture);
        } catch (ResourceNotFoundException | ValidationException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @ApiOperation(value = "Get Facture List.", response = ClientView.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Validation Exception"),
            @ApiResponse(code = 404, message = "Resource Not Found Exception"),
            @ApiResponse(code = 500, message = "Internal Exception")

    })
    @GetMapping("{appUserId}/factures")
    public ResponseEntity<List<Facture>> getAllFactures( Authentication authentication,
                                                         @PathVariable("appUserId") final String appUserId) {

        FactureForm form = new FactureForm();
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
        try {
            List<Facture> factures = factureService.getAllFactures();
            return ResponseEntity.ok(factures);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @ApiOperation(value = "Get Facture Id.", response = ClientView.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Validation Exception"),
            @ApiResponse(code = 404, message = "Resource Not Found Exception"),
            @ApiResponse(code = 500, message = "Internal Exception")

    })
    @GetMapping("{appUserId}/facture/{factureId}")
    public ResponseEntity<Facture> getFactureById(@PathVariable Long factureId, Authentication authentication,
                                                  @PathVariable("appUserId") final String appUserId) {
        FactureForm form = new FactureForm();
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

        Facture facture = factureService.getFactureById(factureId);
        return ResponseEntity.ok(facture);
    }

    @ApiOperation(value = "Update Facture By Id.", response = ClientView.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Validation Exception"),
            @ApiResponse(code = 404, message = "Resource Not Found Exception"),
            @ApiResponse(code = 500, message = "Internal Exception")

    })
    @PutMapping("{appUserId}/facture/{factureId}")
    public ResponseEntity<Facture> updateFacture(@PathVariable Long factureId, @RequestBody FactureForm form,  Authentication authentication,
                                                 @PathVariable("appUserId") final String appUserId) {

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
        Facture factureToUpdate = factureService.getFactureById(factureId);
        Facture updatedFacture = factureService.updateFacture(factureToUpdate);
        return ResponseEntity.ok(updatedFacture);
    }

    @ApiOperation(value = "Delete Facture By Id.", response = ClientView.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Validation Exception"),
            @ApiResponse(code = 404, message = "Resource Not Found Exception"),
            @ApiResponse(code = 500, message = "Internal Exception")

    })
    @DeleteMapping("{appUserId}/facture/{factureId}")
    public ResponseEntity<Void> deleteFacture(@PathVariable Long factureId, Authentication authentication,
                                              @PathVariable("appUserId") final String appUserId) {

        FactureForm form = new FactureForm();
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

        try {
            factureService.deleteFactureById(factureId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("{appUserId}/facture/by-number/{number}")
    public ResponseEntity<Facture> findFactureByNumber(@PathVariable String number, Authentication authentication,
                                                       @PathVariable("appUserId") final String appUserId) {

        FactureForm form = new FactureForm();
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

        Optional<Facture> factureOptional = factureService.findFactureByNumber(number);
        if(factureOptional.isPresent()) {
            return ResponseEntity.ok(factureOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{appUserId}/facture/{factureId}/update-status")
    public ResponseEntity<Facture> updateFactureStatus(@PathVariable Long factureId, @RequestBody FactureStatus newStatus, Authentication authentication,
                                                       @PathVariable("appUserId") final String appUserId) {

        FactureForm form = new FactureForm();
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

        Facture updatedFacture = factureService.updateFactureStatus(factureId, newStatus);
        return ResponseEntity.ok(updatedFacture);
    }


    @PostMapping("{appUserId}/facture/{factureId}/send-email")
    public ResponseEntity<Void> sendFactureByEmail(@PathVariable Long factureId, Authentication authentication,
                                                   @PathVariable("appUserId") final String appUserId) {

        FactureForm form = new FactureForm();
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

        try {
            Facture facture = factureService.getFactureById(factureId);
            factureService.sendFactureByEmail(facture);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error sending facture by email", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("{appUserId}/facture/{factureId}/send-whatsapp")
    public ResponseEntity<Void> sendFactureByWhatsApp(@PathVariable Long factureId, Authentication authentication,
                                                      @PathVariable("appUserId") final String appUserId) {

        FactureForm form = new FactureForm();
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

        try {
            Facture facture = factureService.getFactureById(factureId);
            factureService.sendFactureByWhatsApp(facture);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error sending facture by WhatsApp", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
