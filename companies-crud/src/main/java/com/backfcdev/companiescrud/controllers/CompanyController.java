package com.backfcdev.companiescrud.controllers;


import com.backfcdev.companiescrud.entities.Company;
import com.backfcdev.companiescrud.services.CompanyService;
import io.micrometer.core.annotation.Timed;
import io.micrometer.observation.annotation.Observed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@Tag(name = "Companies resource")
@RequiredArgsConstructor
@RequestMapping(path = "/company")
@Slf4j
@RestController
public class CompanyController {

    private final CompanyService companyService;


    @Operation(summary = "get a company given a company name")
    @GetMapping("/{name}")
    @Observed(name = "company.name")
    @Timed(value = "company.name")
    ResponseEntity<Company> get(@PathVariable String name) {
        log.info("GET: company {}", name);
        return ResponseEntity.ok(this.companyService.readByName(name));
    }

    @Operation(summary = "save in DB a company given a company from body")
    @PostMapping
    ResponseEntity<Company> post(@RequestBody Company company) {
        log.info("POST: company {}", company.getName());
        return ResponseEntity.created(URI.create(this.companyService.create(company).getName()))
                .build();
    }

    @Operation(summary = "update in DB a company given a company from body")
    @PutMapping( "/{name}")
    ResponseEntity<Company> put(@RequestBody Company company, @PathVariable String name) {
        log.info("PUT: company {}", name);
        return ResponseEntity.ok(this.companyService.update(company,name));
    }

    @Operation(summary = "delete in DB a company given a company name")
    @DeleteMapping("/{name}")
    ResponseEntity<?> delete(@PathVariable String name) {
        log.info("DELETE: company {}", name);
        this.companyService.delete(name);
        return ResponseEntity.noContent().build();
    }

}

