package com.utknl.pluto.service;

import com.utknl.pluto.config.property.ReportServiceProperty;
import com.utknl.pluto.model.Report;
import com.utknl.pluto.model.ReportRequest;
import com.utknl.pluto.model.ReportResponse;
import com.utknl.pluto.service.impl.ReportServiceImpl;
import com.utknl.pluto.util.HttpUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTests {
    @Mock
    private RestTemplate restTemplate;
    private ReportService reportService;
    ReportServiceProperty reportServiceProperty = new ReportServiceProperty("http://example.com/");

    @BeforeEach
    public void setUp() {
        reportService = new ReportServiceImpl(restTemplate, reportServiceProperty);
    }

    @Test
    public void shouldReturnReportResponseForValidRequest() {
        ReportRequest reportRequest = new ReportRequest();
        String token = "token";
        Report report = new Report();
        ReportResponse reportResponse = ReportResponse.builder().refundReports(List.of(report)).build();
        ResponseEntity<ReportResponse> responseEntity = ResponseEntity.ok(reportResponse);

        when(restTemplate.exchange(
                reportServiceProperty.getUrl(), HttpMethod.POST, new HttpEntity<>(reportRequest,
                        HttpUtils.generateAuthorizationHeader(token)), ReportResponse.class))
                .thenReturn(responseEntity);

        Optional<ReportResponse> result = reportService.getTransactionsReport(reportRequest, token);

        verify(restTemplate, times(1)).exchange(
                eq(reportServiceProperty.getUrl()), eq(HttpMethod.POST), eq(new HttpEntity<>(reportRequest,
                        HttpUtils.generateAuthorizationHeader(token))), eq(ReportResponse.class));

        assertTrue(result.isPresent());
        assertEquals(reportResponse, result.get());
    }

}
