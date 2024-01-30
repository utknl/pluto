package com.utknl.pluto.controller;

import com.utknl.pluto.model.TransactionListRequest;
import com.utknl.pluto.model.TransactionListResponse;
import com.utknl.pluto.model.TransactionRequest;
import com.utknl.pluto.model.TransactionResponse;
import com.utknl.pluto.model.error.AuthorizationError;
import com.utknl.pluto.model.error.ErrorResponse;
import com.utknl.pluto.service.TransactionService;
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
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Get transaction list")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TransactionListResponse.class))})})
    @PostMapping(path = "/transaction/list", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> getTransactionList(@Valid @RequestBody TransactionListRequest transactionListRequest, BindingResult bindingResult, @RequestHeader HttpHeaders headers) {
        String jwtToken = headers.getFirst(HttpHeaders.AUTHORIZATION);
        if (jwtToken == null) {
            return new ResponseEntity<>(ErrorResponse.create(new AuthorizationError("Token Missed!")), HttpStatus.UNAUTHORIZED);
        }

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(ErrorResponse.create(bindingResult), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return transactionService.getTransactionList(transactionListRequest, jwtToken).map(transactionListResponse -> new ResponseEntity<>(transactionListResponse, HttpStatus.OK)).orElseGet(() -> new ResponseEntity(ErrorResponse.create(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Operation(summary = "Get transaction")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponse.class))})})
    @PostMapping(path = "/transaction", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> getTransaction(@RequestParam(name = "transactionId") String transactionId, @Valid @RequestBody TransactionRequest transactionRequest, BindingResult bindingResult, @RequestHeader HttpHeaders headers) {
        String jwtToken = headers.getFirst(HttpHeaders.AUTHORIZATION);
        if (jwtToken == null) {
            return new ResponseEntity<>(ErrorResponse.create(new AuthorizationError("Token Missed!")), HttpStatus.UNAUTHORIZED);
        }

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(ErrorResponse.create(bindingResult), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return transactionService.getTransaction(transactionRequest, transactionId, jwtToken).map(transactionResponse -> new ResponseEntity<>(transactionResponse, HttpStatus.OK)).orElseGet(() -> new ResponseEntity(ErrorResponse.create(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
