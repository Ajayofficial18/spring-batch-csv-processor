package com.batch_csv_processor.controller;

import com.batch_csv_processor.dto.BatchRequest;
import com.batch_csv_processor.dto.BatchResponse;
import com.batch_csv_processor.dto.UploadResponse;
import com.batch_csv_processor.service.BatchService;
import com.batch_csv_processor.service.FileStorageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/batch")
@RequiredArgsConstructor
public class BatchController {

    private final BatchService batchService;
    private final FileStorageService fileStorageService;

    @PostMapping("/import")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BatchResponse importProducts(
            @Valid @RequestBody BatchRequest request) throws Exception {

        JobExecution execution = batchService.importProducts(request.getFilePath());

        return BatchResponse.builder()
                .status(execution.getStatus().name())
                .message("Batch job started successfully.")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UploadResponse upload(
            @RequestParam("file") MultipartFile file) throws Exception {

        String path = fileStorageService.storeFile(file);

        JobExecution execution = batchService.importProducts(path);

        return UploadResponse.builder()
                .jobExecutionId(execution.getId())
                .status(execution.getStatus().name())
                .message("File uploaded and batch started successfully.")
                .build();
    }

}
