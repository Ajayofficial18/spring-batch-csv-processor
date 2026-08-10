package com.batch_csv_processor.validator;

import com.batch_csv_processor.exception.InvalidJobParameterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
public class JobParameterValidator {

    public void validate(String filePath) {

        if (filePath == null || filePath.isBlank()) {
            throw new InvalidJobParameterException("CSV file path cannot be null or empty.");
        }

        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            throw new InvalidJobParameterException("CSV file does not exist: " + filePath);
        }

        if (!Files.isRegularFile(path)) {
            throw new InvalidJobParameterException("Provided path is not a file: " + filePath);
        }

        if (!Files.isReadable(path)) {
            throw new InvalidJobParameterException("CSV file is not readable: " + filePath);
        }

        if (!filePath.toLowerCase().endsWith(".csv")) {
            throw new InvalidJobParameterException("Only CSV files are supported.");
        }

        try {
            if (Files.size(path) == 0) {
                throw new InvalidJobParameterException("CSV file is empty.");
            }
        } catch (Exception e) {
            throw new InvalidJobParameterException("Unable to access CSV file.");
        }

        log.info("Job parameter validation successful for file: {}", filePath);
    }
}
