package com.utknl.pluto.service;

import com.utknl.pluto.model.TransactionListRequest;
import com.utknl.pluto.model.TransactionListResponse;
import com.utknl.pluto.model.TransactionRequest;
import com.utknl.pluto.model.TransactionResponse;

import java.util.Optional;

public interface TransactionService {
    Optional<TransactionListResponse> getTransactionList(TransactionListRequest transactionListRequest, String jwtToken);

    Optional<TransactionResponse> getTransaction(TransactionRequest transactionRequest, String transactionId, String jwtToken);

}
