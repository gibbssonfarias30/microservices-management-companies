package com.backfcdev.reportms.client;


import com.backfcdev.reportms.config.LoadBalancerConfig;
import com.backfcdev.reportms.models.Company;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name = "companies-crud")
@LoadBalancerClient(name = "companies-crud", configuration = LoadBalancerConfig.class)
public interface CompaniesFeignClient {

    @GetMapping("/companies-crud/company/{name}")
    Optional<Company> getByName(@PathVariable String name);

    @PostMapping("/companies-crud/company")
    Optional<Company> postByName(@RequestBody Company company);

    @DeleteMapping("/companies-crud/company/{name}")
    void deleteByName(@PathVariable String name);
}
