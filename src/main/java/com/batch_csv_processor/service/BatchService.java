package com.batch_csv_processor.service;

import org.springframework.batch.core.JobExecution;

public interface BatchService {

    JobExecution importProducts(String filePath) throws Exception;

}
