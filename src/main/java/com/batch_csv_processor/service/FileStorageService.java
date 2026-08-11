package com.batch_csv_processor.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String storeFile(MultipartFile file) throws Exception;

}
