package com.utknl.pluto.service;

import com.utknl.pluto.model.ClientRequest;
import com.utknl.pluto.model.ClientResponse;

import java.util.Optional;

public interface ClientService {

    Optional<ClientResponse> getClient(ClientRequest clientRequest, String transactionId, String jwtToken);

}
