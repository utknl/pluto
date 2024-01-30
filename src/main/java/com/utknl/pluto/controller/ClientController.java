package com.utknl.pluto.controller;

import com.utknl.pluto.model.ClientRequest;
import com.utknl.pluto.model.ClientResponse;
import com.utknl.pluto.model.error.AuthorizationError;
import com.utknl.pluto.model.error.ErrorResponse;
import com.utknl.pluto.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@SecurityRequirement(name = "Authorization")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(summary = "Get client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ClientResponse.class))})
    })
    @PostMapping(path = "/client", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> getClient(@RequestParam(name = "transactionId") String transactionId,
                                       @Valid @RequestBody ClientRequest clientRequest,
                                       BindingResult bindingResult, @RequestHeader HttpHeaders headers) {
        String jwtToken = headers.getFirst(HttpHeaders.AUTHORIZATION);
        if (jwtToken == null) {
            return new ResponseEntity<>(ErrorResponse.create(new AuthorizationError("Token Missed!")), HttpStatus.UNAUTHORIZED);
        }

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(ErrorResponse.create(bindingResult), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return clientService.getClient(clientRequest, transactionId, jwtToken)
                .map(clientResponse -> new ResponseEntity<>(clientResponse, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity(ErrorResponse.create(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
