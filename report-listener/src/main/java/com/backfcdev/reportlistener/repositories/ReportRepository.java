package com.backfcdev.reportlistener.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.backfcdev.reportlistener.documents.ReportDocument;

public interface ReportRepository extends MongoRepository<ReportDocument, String> {
}
