package com.utknl.pluto.service.impl;

import com.utknl.pluto.config.property.ClientServiceProperty;
import com.utknl.pluto.model.ClientRequest;
import com.utknl.pluto.model.ClientResponse;
import com.utknl.pluto.service.ClientService;
import com.utknl.pluto.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    private final RestTemplate restTemplate;
    private final String url;

    @Autowired
    public ClientServiceImpl(RestTemplate restTemplate, ClientServiceProperty property) {
        this.restTemplate = restTemplate;
        url = property.getUrl();
    }

    @Override
    public Optional<ClientResponse> getClient(ClientRequest clientRequest, String transactionId, String jwtToken) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("transactionId", transactionId);
        String finalUrl = builder.build().toUriString();

        ClientResponse clientResponse = restTemplate.exchange(finalUrl, HttpMethod.POST,
                new HttpEntity<>(clientRequest, HttpUtils.generateAuthorizationHeader(jwtToken)), ClientResponse.class).getBody();
        assert clientResponse != null;
        return Optional.of(clientResponse);
    }

}
