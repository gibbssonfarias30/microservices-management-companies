package com.backfcdev.reportms.streams;


import com.backfcdev.reportms.models.Company;
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

    /*
    topic name -> consumerSbReport
     */

    public Company publishCbReport(String company) {
        this.streamBridge.send("consumerSbReport", company);
        this.streamBridge.send("consumerSbReport-in-0", company);
        this.streamBridge.send("consumerSbReport-out-0", company);
        return Company.builder().build();
    }
}
