package com.utknl.pluto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class MerchantTransaction {

    private String referenceNo;
    private String status;
    private String customData;
    private String type;
    private String operation;
    @JsonProperty("created_at")
    private String created_at;
    private String message;
    private String transactionId;

}
