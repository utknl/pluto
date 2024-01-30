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
public class CustomerInfo {

    private String billingFirstName;
    private String billingLastName;
    private String email;
    private String billingCompany;
    private String billingCity;
    @JsonProperty("created_at")
    private String created_at;
    @JsonProperty("updated_at")
    private String updated_at;
    private Long id;
    private String billingAddress1;

}
