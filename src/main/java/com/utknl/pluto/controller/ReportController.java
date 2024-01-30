package com.utknl.pluto.controller;

import com.utknl.pluto.model.ReportRequest;
import com.utknl.pluto.model.ReportResponse;
import com.utknl.pluto.model.error.AuthorizationError;
import com.utknl.pluto.model.error.ErrorResponse;
import com.utknl.pluto.service.ReportService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "Authorization")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @Operation(summary = "Get transactions report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReportResponse.class))})
    })
    @PostMapping(path = "/transactions/report", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> getTransactionsReport(@Valid @RequestBody ReportRequest reportRequest, BindingResult bindingResult, @RequestHeader HttpHeaders headers) {
        String jwtToken = headers.getFirst(HttpHeaders.AUTHORIZATION);
        if (jwtToken == null) {
            return new ResponseEntity<>(ErrorResponse.create(new AuthorizationError("Token Missed!")), HttpStatus.UNAUTHORIZED);
        }

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(ErrorResponse.create(bindingResult), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return reportService.getTransactionsReport(reportRequest, jwtToken)
                .map(refundReportResponse -> new ResponseEntity<>(refundReportResponse, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity(ErrorResponse.create(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

}