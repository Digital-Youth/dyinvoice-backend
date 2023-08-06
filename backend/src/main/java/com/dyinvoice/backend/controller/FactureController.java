package com.dyinvoice.backend.controller;


import com.dyinvoice.backend.security.JwtTokenProvider;
import com.dyinvoice.backend.service.FactureService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="FactureController", description="Rest API for Facture operations.")
@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping(value = "/v1/user")
public class FactureController {

    private static final Logger logger = LoggerFactory.getLogger(FactureController.class);

    private final FactureService factureService;
    private final JwtTokenProvider jwtTokenProvider;
}
