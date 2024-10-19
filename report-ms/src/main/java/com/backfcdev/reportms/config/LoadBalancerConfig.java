package com.backfcdev.reportms.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@Slf4j
public class LoadBalancerConfig {

    @Bean
    ServiceInstanceListSupplier serviceInstanceListSupplier(ConfigurableApplicationContext context) {
        log.info("Configuring load balanced");
        return ServiceInstanceListSupplier.builder()
                .withBlockingDiscoveryClient()
                .withSameInstancePreference()
                .build(context);
    }
}
