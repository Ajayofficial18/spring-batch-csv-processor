package com.batch_csv_processor.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BatchResponse {

    private String message;

    private String status;

    private LocalDateTime timestamp;

}
