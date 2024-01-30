package com.utknl.pluto.service;

import com.utknl.pluto.config.property.TransactionServiceProperty;
import com.utknl.pluto.model.*;
import com.utknl.pluto.service.impl.TransactionServiceImpl;
import com.utknl.pluto.util.HttpUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTests {
    @Mock
    private RestTemplate restTemplate;
    private TransactionService transactionService;
    TransactionServiceProperty transactionServiceProperty = new TransactionServiceProperty("http://example.com/list", "http://example.com/");

    @BeforeEach
    public void setUp() {
        transactionService = new TransactionServiceImpl(restTemplate, transactionServiceProperty);
    }

    @Test
    public void shouldReturnTransactionResponseForValidRequest() {
        TransactionRequest transactionRequest = TransactionRequest.builder().transactionId("transactionId").build();
        String transactionId = "transactionId";
        String token = "token";
        CustomerInfo customerInfo = new CustomerInfo();
        Fx fx = new Fx();
        MerchantTransaction merchantTransaction = new MerchantTransaction();
        Merchant merchant = new Merchant();
        TransactionResponse transactionResponse = TransactionResponse.builder()
                .customerInfo(customerInfo).fx(fx).merchant(merchant).transaction(merchantTransaction).build();
        ResponseEntity<TransactionResponse> responseEntity = new ResponseEntity<>(transactionResponse, HttpStatus.OK);

        when(restTemplate.exchange(
                "http://example.com/?transactionId=transactionId", HttpMethod.POST, new HttpEntity<>(transactionRequest,
                        HttpUtils.generateAuthorizationHeader(token)), TransactionResponse.class))
                .thenReturn(responseEntity);

        Optional<TransactionResponse> result = transactionService.getTransaction(transactionRequest, transactionId, token);

        verify(restTemplate, times(1)).exchange(
                eq("http://example.com/?transactionId=transactionId"), eq(HttpMethod.POST), eq(new HttpEntity<>(transactionRequest,
                        HttpUtils.generateAuthorizationHeader(token))), eq(TransactionResponse.class));

        Assertions.assertTrue(result.isPresent());
        assertEquals(transactionResponse, result.get());
    }

    @Test
    public void shouldReturnTransactionListResponseForValidRequest() {
        TransactionListRequest transactionListRequest = TransactionListRequest.builder().build();
        String token = "token";
        Data data = new Data();
        TransactionListResponse transactionListResponse = TransactionListResponse.builder().data(List.of(data)).build();
        ResponseEntity<TransactionListResponse> responseEntity = new ResponseEntity<>(transactionListResponse, HttpStatus.OK);

        when(restTemplate.exchange(
                "http://example.com/list", HttpMethod.POST, new HttpEntity<>(transactionListRequest,
                        HttpUtils.generateAuthorizationHeader(token)), TransactionListResponse.class))
                .thenReturn(responseEntity);

        Optional<TransactionListResponse> result = transactionService.getTransactionList(transactionListRequest, token);

        verify(restTemplate, times(1)).exchange(
                eq("http://example.com/list"), eq(HttpMethod.POST), eq(new HttpEntity<>(transactionListRequest,
                        HttpUtils.generateAuthorizationHeader(token))), eq(TransactionListResponse.class));

        Assertions.assertTrue(result.isPresent());
        assertEquals(transactionListResponse, result.get());
    }

}
