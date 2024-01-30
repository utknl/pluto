package com.utknl.pluto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class Merchant {

    private Integer id;
    private String name;
    private Boolean allowPartialRefund;
    private Boolean allowPartialCapture;

}
