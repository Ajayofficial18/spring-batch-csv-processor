package com.batch_csv_processor.service.impl;

import com.batch_csv_processor.config.FileStorageProperties;
import com.batch_csv_processor.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final FileStorageProperties properties;

    @Override
    public String storeFile(MultipartFile file) throws Exception {

        Path uploadDir = Paths.get(properties.getDirectory());

        Files.createDirectories(uploadDir);

        String originalName = StringUtils.cleanPath(file.getOriginalFilename());

        String storedName = UUID.randomUUID() + "_" + originalName;

        Path destination = uploadDir.resolve(storedName);

        Files.copy(
                file.getInputStream(),
                destination,
                StandardCopyOption.REPLACE_EXISTING
        );

        return destination.toAbsolutePath().toString();
    }
}
