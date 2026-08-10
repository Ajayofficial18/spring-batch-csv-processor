package com.batch_csv_processor.listner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Component
public class ProductJobExecutionListener implements JobExecutionListener {

    private LocalDateTime startTime;

    @Override
    public void beforeJob(JobExecution jobExecution) {

        startTime = LocalDateTime.now();

        log.info("========================================");
        log.info("Product Import Job Started");
        log.info("Job Name : {}", jobExecution.getJobInstance().getJobName());
        log.info("Job Id   : {}", jobExecution.getJobId());
        log.info("========================================");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        LocalDateTime endTime = LocalDateTime.now();

        Duration duration = Duration.between(startTime, endTime);

        log.info("========================================");
        log.info("Product Import Job Finished");
        log.info("Status : {}", jobExecution.getStatus());
        log.info("Time   : {} Seconds", duration.getSeconds());

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Job Completed Successfully");
        } else {
            log.error("Job Failed");
        }

        log.info("========================================");
    }
}