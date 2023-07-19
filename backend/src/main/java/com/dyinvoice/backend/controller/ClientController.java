package com.dyinvoice.backend.controller;


import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.exception.ValidationException;
import com.dyinvoice.backend.model.entity.Client;
import com.dyinvoice.backend.model.entity.EntitiesRoleName;
import com.dyinvoice.backend.model.form.ClientForm;
import com.dyinvoice.backend.model.view.ClientView;
import com.dyinvoice.backend.security.JwtTokenProvider;
import com.dyinvoice.backend.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;



@Api(value="ClientController", description="Rest API for Client operations.")
@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping(value = "/v1/user")
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(AppUserController.class);
    private ClientService clientService;
    JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "Register User.", response = ClientView.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Validation Exception"),
            @ApiResponse(code = 404, message = "Resource Not Found Exception"),
            @ApiResponse(code = 500, message = "Internal Exception")

    })
    @PostMapping("{appUserId}/client/createClient")
    public ResponseEntity<Client> createClient(@Valid @RequestBody ClientForm form,
                                               Authentication authentication,
                                               @PathVariable("appUserId") final String appUserId,
                                               HttpServletRequest request
                                               ) throws ValidationException, ResourceNotFoundException {

        String jwtToken = request.getHeader("Authorization").substring(7);
        logger.debug(jwtToken);


        boolean useId = false;
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals( EntitiesRoleName.ROLE_ADMIN)
                        || a.getAuthority().equals(EntitiesRoleName.ROLE_STAFF) )) {
            useId = true;
        }

        if(useId){
            try {
                form.setId(Long.parseLong(appUserId));
            } catch(NumberFormatException e) {
                form.setEmail(appUserId); // If not a long, assume it's an email
            }
        } else {
            try {
                form.setId(Long.parseLong(authentication.getName()));
            } catch(NumberFormatException e) {
                form.setEmail(authentication.getName()); // If not a long, assume it's an email
            }
        }
            Client createdClient = clientService.createClient(form);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);

    }



    @ApiOperation(value = "Get all clients.", response = ClientView.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Exception")
    })
    @GetMapping("{appUserId}/client")
    public ResponseEntity<List<Client>> getAllClients(Authentication authentication,
                                                      @PathVariable("appUserId") final String appUserId) {

        ClientForm form = new ClientForm();

        boolean useId = false;
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals( EntitiesRoleName.ROLE_ADMIN)
                        || a.getAuthority().equals(EntitiesRoleName.ROLE_STAFF) )) {
            useId = true;
        }

        if(useId){
            try {
                form.setId(Long.parseLong(appUserId));
            } catch(NumberFormatException e) {
                form.setEmail(appUserId); // If not a long, assume it's an email
            }
        } else {
            try {
                form.setId(Long.parseLong(authentication.getName()));
            } catch(NumberFormatException e) {
                form.setEmail(authentication.getName()); // If not a long, assume it's an email
            }
        }

        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }



    @ApiOperation(value = "Get client by ID.", response = ClientView.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Resource Not Found Exception"),
            @ApiResponse(code = 500, message = "Internal Exception")
    })
    @GetMapping("{appUserId}/client/{idOrEmail}")
    public ResponseEntity<Client> getClientById(@PathVariable("idOrEmail") final String idOrEmail,
                                                Authentication authentication,
                                                @PathVariable("appUserId") final String appUserId) throws ResourceNotFoundException {
        ClientForm form = new ClientForm();

        boolean useId = false;
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals( EntitiesRoleName.ROLE_ADMIN)
                        || a.getAuthority().equals(EntitiesRoleName.ROLE_STAFF) )) {
            useId = true;
        }

        if (useId) {
            try {
                form.setId(Long.parseLong(appUserId));
            } catch (NumberFormatException e) {
                form.setEmail(appUserId); // If not a long, assume it's an email
            }
        } else {
            try {
                form.setId(Long.parseLong(authentication.getName()));
            } catch (NumberFormatException e) {
                form.setEmail(authentication.getName()); // If not a long, assume it's an email
            }
        }

        Client client;
        try {
            // Try as an ID first
            long id = Long.parseLong(idOrEmail);
            client = clientService.getClientById(id);
        } catch (NumberFormatException e) {
            // If it's not a number, treat it as an email
            client = clientService.getClientByEmail(idOrEmail);
        }

        if (client == null) {
            throw new ResourceNotFoundException("Client not found.");
        }
        return ResponseEntity.ok(client);
    }




    @ApiOperation(value = "Update client.", response = ClientView.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Validation Exception"),
            @ApiResponse(code = 404, message = "Resource Not Found Exception"),
            @ApiResponse(code = 500, message = "Internal Exception")
    })
    @PutMapping("{appUserId}/clients/updateClient")
    public ResponseEntity<Client> updateClient(@Valid @RequestBody Client client) throws ValidationException, ResourceNotFoundException {
        Client updatedClient = clientService.updateClient(client);
        if (updatedClient == null) {
            throw new ValidationException("Client update failed.");
        }
        return ResponseEntity.ok(updatedClient);
    }


    @ApiOperation(value = "Delete client by ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Resource Not Found Exception"),
            @ApiResponse(code = 500, message = "Internal Exception")
    })
    @DeleteMapping("{appUserId}/client/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable long id,
                                             Authentication authentication,
                                             @PathVariable("appUserId") final String appUserId) {

        ClientForm form = new ClientForm();

        boolean useId = false;
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals( EntitiesRoleName.ROLE_ADMIN)
                        || a.getAuthority().equals(EntitiesRoleName.ROLE_STAFF) )) {
            useId = true;
        }

        if(useId){
            try {
                form.setId(Long.parseLong(appUserId));
            } catch(NumberFormatException e) {
                form.setEmail(appUserId); // If not a long, assume it's an email
            }
        } else {
            try {
                form.setId(Long.parseLong(authentication.getName()));
            } catch(NumberFormatException e) {
                form.setEmail(authentication.getName()); // If not a long, assume it's an email
            }
        }
        clientService.deleteClient(id);
        return ResponseEntity.ok().build();
    }

}
