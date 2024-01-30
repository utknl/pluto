package com.utknl.pluto.service.impl;

import com.utknl.pluto.config.property.ReportServiceProperty;
import com.utknl.pluto.model.ReportRequest;
import com.utknl.pluto.model.ReportResponse;
import com.utknl.pluto.service.ReportService;
import com.utknl.pluto.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {
    private final RestTemplate restTemplate;
    private final String url;

    @Autowired
    public ReportServiceImpl(RestTemplate restTemplate, ReportServiceProperty property) {
        this.restTemplate = restTemplate;
        url = property.getUrl();
    }

    @Override
    public Optional<ReportResponse> getTransactionsReport(ReportRequest reportRequest, String jwtToken) {
        ReportResponse transactionsReportResponse = restTemplate.exchange(url, HttpMethod.POST,
                new HttpEntity<>(reportRequest, HttpUtils.generateAuthorizationHeader(jwtToken)), ReportResponse.class).getBody();

        assert transactionsReportResponse != null;
        return Optional.of(transactionsReportResponse);
    }

}
