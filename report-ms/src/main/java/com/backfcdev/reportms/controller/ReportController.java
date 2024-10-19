package com.backfcdev.reportms.controller;


import com.backfcdev.reportms.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping( "report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;


    @GetMapping( "/{name}")
    ResponseEntity<Map<String, String>> getReport(@PathVariable String name) {
        var response = Map.of("report", this.reportService.makeReport(name));
        return ResponseEntity.ok(response);
    }


    @PostMapping
    ResponseEntity<String> postReport(@RequestBody String report) {
        return ResponseEntity.ok(this.reportService.saveReport(report));
    }

    @DeleteMapping("/{name}")
    ResponseEntity<Void> delete(@PathVariable String name) {
        this.reportService.deleteReport(name);
        return ResponseEntity.noContent().build();
    }
}
