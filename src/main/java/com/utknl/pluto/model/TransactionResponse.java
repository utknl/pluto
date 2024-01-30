package com.utknl.pluto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class TransactionResponse {

    private Fx fx;
    private CustomerInfo customerInfo;
    private Merchant merchant;
    private MerchantTransaction transaction;

}
