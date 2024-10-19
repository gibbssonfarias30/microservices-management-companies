package com.backfcdev.companiescrud.config;


import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(999)
@Slf4j
public class LogObservationHandler implements ObservationHandler<Observation.Context> {


    @Override
    public void onStart(Observation.Context context) {
        log.info("LogObservationHandler::onStart: {}", context.getName());
    }

    @Override
    public void onError(Observation.Context context) {
        log.info("LogObservationHandler::onError: {}", context.getName());
    }

    @Override
    public void onStop(Observation.Context context) {
        log.info("LogObservationHandler::onStop: {}", context.getName());
    }

    @Override
    public boolean supportsContext(Observation.Context context) {
        return true;
    }
}
