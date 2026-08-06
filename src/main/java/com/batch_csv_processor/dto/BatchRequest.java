package com.batch_csv_processor.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BatchRequest {

    @NotBlank(message = "File path is required")
    private String filePath;

}