package com.utknl.pluto.service.impl;

import com.utknl.pluto.config.property.TransactionServiceProperty;
import com.utknl.pluto.model.TransactionListRequest;
import com.utknl.pluto.model.TransactionListResponse;
import com.utknl.pluto.model.TransactionRequest;
import com.utknl.pluto.model.TransactionResponse;
import com.utknl.pluto.service.TransactionService;
import com.utknl.pluto.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final RestTemplate restTemplate;
    private final String listUrl;
    private final String url;

    @Autowired
    public TransactionServiceImpl(RestTemplate restTemplate, TransactionServiceProperty property) {
        this.restTemplate = restTemplate;
        listUrl = property.getList();
        url = property.getUrl();
    }

    @Override
    public Optional<TransactionListResponse> getTransactionList(TransactionListRequest transactionListRequest, String jwtToken) {
        TransactionListResponse listResponse = restTemplate.exchange(listUrl, HttpMethod.POST,
                new HttpEntity<>(transactionListRequest, HttpUtils.generateAuthorizationHeader(jwtToken)), TransactionListResponse.class).getBody();

        assert listResponse != null;
        return Optional.of(listResponse);
    }

    @Override
    public Optional<TransactionResponse> getTransaction(TransactionRequest transactionRequest, String transactionId, String jwtToken) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("transactionId", transactionId);
        String finalUrl = builder.build().toUriString();

        TransactionResponse transactionResponse = restTemplate.exchange(finalUrl, HttpMethod.POST,
                new HttpEntity<>(transactionRequest, HttpUtils.generateAuthorizationHeader(jwtToken)), TransactionResponse.class).getBody();
        assert transactionResponse != null;
        return Optional.of(transactionResponse);
    }

}
