package com.batch_csv_processor.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadResponse {

    private Long jobExecutionId;
    private String status;
    private String message;

}