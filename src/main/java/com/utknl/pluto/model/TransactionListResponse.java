package com.utknl.pluto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionListResponse {

    @JsonProperty("current_page")
    private String currentPage;
    private List<Data> data;
    @JsonProperty("first_page_url")
    private String firstPageUrl;
    private Integer from;
    @JsonProperty("next_page_url")
    private String nextPageUrl;
    private String path;
    @JsonProperty("per_page")
    private Integer perPage;
    @JsonProperty("prev_page_url")
    private String prevPageUrl;
    private Integer to;

}
