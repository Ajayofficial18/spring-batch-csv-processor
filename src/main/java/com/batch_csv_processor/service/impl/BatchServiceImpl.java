package com.batch_csv_processor.service.impl;

import com.batch_csv_processor.service.BatchService;
import com.batch_csv_processor.validator.JobParameterValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatchServiceImpl implements BatchService {

    private final JobLauncher jobLauncher;
    private final Job importProductJob;
    private final JobParameterValidator jobParameterValidator;

    @Override
    public JobExecution importProducts(String filePath) throws Exception {

        jobParameterValidator.validate(filePath);

        JobParameters parameters = new JobParametersBuilder()
                .addString("file", filePath)
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();

        return jobLauncher.run(importProductJob, parameters);
    }
}
