package com.backfcdev.reportms.services;


import com.backfcdev.reportms.client.CompaniesFeignClient;
import com.backfcdev.reportms.helpers.ReportHelper;
import com.backfcdev.reportms.models.Company;
import com.backfcdev.reportms.models.WebSite;
import com.backfcdev.reportms.repository.CompaniesFallbackRepository;
import com.backfcdev.reportms.streams.ReportPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;


@RequiredArgsConstructor
@Slf4j
@Service
public class ReportServiceImpl implements ReportService {


    private final CompaniesFeignClient companiesFeignClient;
    private final ReportHelper reportHelper;
    private final CompaniesFallbackRepository companiesFallbackRepository;
    private final Resilience4JCircuitBreakerFactory resilience4JCircuitBreakerFactory;
    private final ReportPublisher reportPublisher;


    @Override
    public String makeReport(String name) {
        var circuitBreaker = this.resilience4JCircuitBreakerFactory.create("companies-circuitbreaker");
        return circuitBreaker.run(
                () -> this.makeReportMain(name),
                throwable -> this.makeReportFallback(name, throwable)
        );
    }

    @Override
    public String saveReport(String report) {
        var format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var placeholders = this.reportHelper.getPlaceholdersFromTemplate(report);
        var webSites = Stream.of(placeholders.get(3))
                .map(website -> WebSite.builder().name(website).build())
                .toList();

        var company = Company.builder()
                .name(placeholders.get(0))
                .foundationDate(LocalDate.parse(placeholders.get(1), format))
                .founder(placeholders.get(2))
                .webSites(webSites)
                .build();

        this.reportPublisher.publishReport(report);
        this.companiesFeignClient.postByName(company);
        return "Saved";
    }

    @Override
    public void deleteReport(String name) {
        this.companiesFeignClient.deleteByName(name);
    }

    private String makeReportMain(String name) {
        return reportHelper.readTemplate(this.companiesFeignClient.getByName(name).orElseThrow());

    }

    private String makeReportFallback(String name, Throwable throwable) {
        log.warn(throwable.getMessage());
        return reportHelper.readTemplate(this.companiesFallbackRepository.getByName(name));

    }
}
