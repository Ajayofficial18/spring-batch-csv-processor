package com.batch_csv_processor.exception;

public class InvalidJobParameterException extends RuntimeException {

    public InvalidJobParameterException(String message) {
        super(message);
    }
}
