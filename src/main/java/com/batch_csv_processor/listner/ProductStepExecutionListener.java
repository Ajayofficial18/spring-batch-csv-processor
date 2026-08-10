package com.batch_csv_processor.listner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductStepExecutionListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {

        log.info("========================================");
        log.info("Step Started");
        log.info("Step Name : {}", stepExecution.getStepName());
        log.info("========================================");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        log.info("========================================");
        log.info("Step Completed");
        log.info("Read Count      : {}", stepExecution.getReadCount());
        log.info("Write Count     : {}", stepExecution.getWriteCount());
        log.info("Filter Count    : {}", stepExecution.getFilterCount());
        log.info("Read Skip Count : {}", stepExecution.getReadSkipCount());
        log.info("Process Skip    : {}", stepExecution.getProcessSkipCount());
        log.info("Write Skip      : {}", stepExecution.getWriteSkipCount());
        log.info("Commit Count    : {}", stepExecution.getCommitCount());
        log.info("Rollback Count  : {}", stepExecution.getRollbackCount());
        log.info("Exit Status     : {}", stepExecution.getExitStatus());
        log.info("========================================");

        return stepExecution.getExitStatus();
    }
}

