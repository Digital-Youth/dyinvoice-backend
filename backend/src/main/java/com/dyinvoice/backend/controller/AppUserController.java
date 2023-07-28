package com.dyinvoice.backend.controller;


import com.dyinvoice.backend.dao.implementation.ClientDAOImpl;
import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.exception.ValidationException;
import com.dyinvoice.backend.model.entity.AppUser;
import com.dyinvoice.backend.model.entity.EntitiesRoleName;
import com.dyinvoice.backend.model.entity.Invitations;
import com.dyinvoice.backend.model.form.AppUserForm;
import com.dyinvoice.backend.model.form.InvitationForm;
import com.dyinvoice.backend.model.form.LoginForm;
import com.dyinvoice.backend.model.form.RegisterForm;
import com.dyinvoice.backend.model.response.JWTLoginResponse;
import com.dyinvoice.backend.model.view.AppUserView;
import com.dyinvoice.backend.repository.AppUserRepository;
import com.dyinvoice.backend.repository.InvitationRepository;
import com.dyinvoice.backend.security.JwtTokenProvider;
import com.dyinvoice.backend.service.AppUserService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Api(value="AppUserController", description="Rest API for App User operations.")
@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping(value = "/v1/user")
public class AppUserController {

    private AppUserService appUserService;
    private InvitationRepository invitationRepository;
    private AppUserRepository appUserRepository;
    PasswordEncoder passwordEncoder;
    JwtTokenProvider jwtTokenProvider;

    private static final Logger logger = LoggerFactory.getLogger(AppUserController.class);



    @ApiOperation(value = "Get App user profile by ID.", response = AppUserView.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Validation Exception"),
            @ApiResponse(code = 404, message = "Resource Not Found Exception"),
            @ApiResponse(code = 500, message = "Internal Exception")

    })
    @GetMapping
    public AppUserView getUserInfo(
            final String appUserId,
            Authentication authentication,
            HttpServletRequest request) throws ValidationException, ResourceNotFoundException {

        String jwtToken = request.getHeader("Authorization").substring(7);
        logger.debug(jwtToken);

        // Vérifier que l'utilisateur demandé est le même que l'utilisateur authentifié
      //  String authenticatedUsername = jwtTokenProvider.getEmail(jwtToken);

/*        if (!authenticatedUsername.equals(appUserId)) {
            throw new AccessDeniedException("L'utilisateur authentifié n'a pas la permission de voir les détails d'un autre utilisateur");
        }*/

        AppUserForm form = new AppUserForm();
        boolean useId = false;

        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(EntitiesRoleName.ROLE_ADMIN)
                        || a.getAuthority().equals(EntitiesRoleName.ROLE_ADMIN) )) {
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

        return appUserService.getUserInfo(jwtToken);
    }




    @ApiOperation(value = "Get App user profile by ID.", response = AppUserView.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Validation Exception"),
            @ApiResponse(code = 404, message = "Resource Not Found Exception"),
            @ApiResponse(code = 500, message = "Internal Exception")

    })

    @GetMapping(value="/{appUserId}")
    public AppUserView getUserInfoById(
            @ApiParam(value = "AppUser ID.", name = "appUserId", required = true)
            @PathVariable("appUserId") final String appUserId,
            Authentication authentication,  HttpServletRequest request) throws ValidationException, ResourceNotFoundException {

        String jwtToken = request.getHeader("Authorization").substring(7);
        logger.debug(jwtToken);

        // Vérifier que l'utilisateur demandé est le même que l'utilisateur authentifié
        AppUserForm form = new AppUserForm();

        boolean useId = false;

        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(EntitiesRoleName.ROLE_ADMIN)
                        || a.getAuthority().equals(EntitiesRoleName.ROLE_ADMIN) )) {
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

        return appUserService.getAppUserInfoById(form);
    }

    @ApiOperation(value = "Register User.", response = AppUserView.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Validation Exception"),
            @ApiResponse(code = 404, message = "Resource Not Found Exception"),
            @ApiResponse(code = 500, message = "Internal Exception")

    })
    @PostMapping(value = "/register")
    public ResponseEntity<AppUser> registerUser(@Valid @RequestBody RegisterForm form) throws ValidationException, ResourceNotFoundException {
        AppUser appUser = appUserService.registerUser(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(appUser);
    }


    @ApiOperation(value = "Login User.", response = AppUserView.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Validation Exception"),
            @ApiResponse(code = 404, message = "Resource Not Found Exception"),
            @ApiResponse(code = 500, message = "Internal Exception")

    })
    @PostMapping(value = "/login")
    public ResponseEntity<JWTLoginResponse> loginUser(@RequestBody LoginForm form) throws ValidationException, ResourceNotFoundException {
        String token = appUserService.loginUser(form);

        JWTLoginResponse response = new JWTLoginResponse();
        response.setAccessToken(token);

        return ResponseEntity.ok(response);
    }




    @ApiOperation(value = "Update User.", response = AppUserView.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Validation Exception"),
            @ApiResponse(code = 404, message = "Resource Not Found Exception"),
            @ApiResponse(code = 500, message = "Internal Exception")
    })
    @PutMapping(value = "/{appUserId}")
    public ResponseEntity<AppUser> updateUser(@PathVariable("appUserId") final String appUserId,
                                                  @Valid @RequestBody AppUserForm form,
                                              HttpServletRequest request)
            throws ValidationException, ResourceNotFoundException {

        String jwtToken = request.getHeader("Authorization").substring(7);
        logger.debug(jwtToken);
        form.setId(Long.parseLong(appUserId));
        AppUser updatedUser = appUserService.updateAppUser(form);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }




    @ApiOperation(value = "Create Invitation.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Validation Exception"),
            @ApiResponse(code = 404, message = "Resource Not Found Exception"),
            @ApiResponse(code = 500, message = "Internal Exception")
    })
    @PostMapping("{appUserId}/team")
    public String createInvitation(@RequestBody InvitationForm form,  @PathVariable("appUserId") final String appUserId, Authentication authentication)
            throws ValidationException, ResourceNotFoundException {

        if(authentication == null) {
            throw new AccessDeniedException("Vous devez être authentifié pour accéder à cette ressource.");
        }

        boolean useId = false;
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals( EntitiesRoleName.ROLE_ADMIN)
                        || a.getAuthority().equals(EntitiesRoleName.ROLE_ADMIN) )) {
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
                assert authentication != null;
                form.setId(Long.parseLong(authentication.getName()));
            } catch(NumberFormatException e) {
                form.setEmail(authentication.getName()); // If not a long, assume it's an email
            }
        }

        return appUserService.createInvitation(form);
    }


    @GetMapping("/accept-invitation")
    public String showSetPasswordPage(@RequestParam("token") String token, Model model) {
        Optional<Invitations> invitationOpt = invitationRepository.findByToken(token);
        if (invitationOpt.isEmpty() || invitationOpt.get().getExpiryDate().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "Invalid or expired token");
            return "error";
        }

        model.addAttribute("token", token);
        return "redirect:/set-password";
    }
    private String generateRandomPassword() {
        return UUID.randomUUID().toString();
    }
    @PostMapping("/accept-invitation")
    public String handleSetPassword(@RequestParam("token") String token, @RequestParam("password") String password, Model model) throws ResourceNotFoundException {

        String randomPassword = generateRandomPassword();
        Optional<Invitations> invitationOpt = invitationRepository.findByToken(token);
        if (invitationOpt.isEmpty() || invitationOpt.get().getExpiryDate().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "Invalid or expired token");
            return "error";
        }

        Invitations invitation = invitationOpt.get();
        AppUser user = appUserRepository.findById(invitation.getAppUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setPassword(passwordEncoder.encode(randomPassword));
        appUserRepository.save(user);
        invitationRepository.delete(invitation);

        model.addAttribute("message", "Password set successfully");
        return "login";
    }







}
