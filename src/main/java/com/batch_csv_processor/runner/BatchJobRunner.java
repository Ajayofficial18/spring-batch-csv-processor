package com.batch_csv_processor.runner;

import com.batch_csv_processor.service.BatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchJobRunner implements CommandLineRunner {

    private final BatchService batchService;

    @Override
    public void run(String... args) throws Exception {

//        batchService.importProducts(
//                "D:/BatchFiles/product_details.csv"
//        );
        System.out.println("job runner running its job");
    }
}
