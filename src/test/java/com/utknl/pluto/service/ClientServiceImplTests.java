package com.utknl.pluto.service;

import com.utknl.pluto.config.property.ClientServiceProperty;
import com.utknl.pluto.model.ClientRequest;
import com.utknl.pluto.model.ClientResponse;
import com.utknl.pluto.model.CustomerInfo;
import com.utknl.pluto.service.impl.ClientServiceImpl;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTests {
    @Mock
    private RestTemplate restTemplate;
    private ClientService clientService;
    ClientServiceProperty clientServiceProperty = new ClientServiceProperty("http://example.com/");

    @BeforeEach
    public void setUp() {
        clientService = new ClientServiceImpl(restTemplate, clientServiceProperty);
    }

    @Test
    public void shouldReturnClientResponseForValidRequest() {
        ClientRequest clientRequest = ClientRequest.builder().transactionId("transactionId").build();
        String transactionId = "transactionId";
        String token = "token";
        CustomerInfo customerInfo = new CustomerInfo();
        ClientResponse clientResponse = ClientResponse.builder().customerInfo(customerInfo).build();
        ResponseEntity<ClientResponse> responseEntity = new ResponseEntity<>(clientResponse, HttpStatus.OK);

        when(restTemplate.exchange(
                "http://example.com/?transactionId=transactionId", HttpMethod.POST, new HttpEntity<>(clientRequest,
                        HttpUtils.generateAuthorizationHeader(token)), ClientResponse.class))
                .thenReturn(responseEntity);

        Optional<ClientResponse> result = clientService.getClient(clientRequest, transactionId, token);

        verify(restTemplate, times(1)).exchange(
                eq("http://example.com/?transactionId=transactionId"), eq(HttpMethod.POST), eq(new HttpEntity<>(clientRequest,
                        HttpUtils.generateAuthorizationHeader(token))), eq(ClientResponse.class));

        Assertions.assertTrue(result.isPresent());
        assertEquals(customerInfo, result.get().getCustomerInfo());
    }

}
