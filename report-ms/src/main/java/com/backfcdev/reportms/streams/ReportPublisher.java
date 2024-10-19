package com.backfcdev.reportms.streams;


import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ReportPublisher {

    private final StreamBridge streamBridge;

    /*
    topic name -> consumerReport
     */

    public void publishReport(String report) {
        this.streamBridge.send("consumerReport", report);
        this.streamBridge.send("consumerReport-in-0", report);
        this.streamBridge.send("consumerReport-out-0", report);
    }
}
