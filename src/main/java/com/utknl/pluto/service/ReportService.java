package com.utknl.pluto.service;

import com.utknl.pluto.model.ReportRequest;
import com.utknl.pluto.model.ReportResponse;

import java.util.Optional;

public interface ReportService {

    Optional<ReportResponse> getTransactionsReport(ReportRequest reportRequest, String jwtToken);

}
