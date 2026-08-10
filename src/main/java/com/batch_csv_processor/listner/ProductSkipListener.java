package com.batch_csv_processor.listner;

import com.batch_csv_processor.dto.ProductCsv;
import com.batch_csv_processor.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductSkipListener implements SkipListener<ProductCsv, Product> {

    @Override
    public void onSkipInRead(Throwable throwable) {

        log.error("Record skipped while reading. Reason: {}",
                throwable.getMessage());
    }

    @Override
    public void onSkipInProcess(ProductCsv item, Throwable throwable) {

        log.error("""
                
================== SKIPPED RECORD ==================
Product Id   : {}
Product Name : {}
Reason       : {}
====================================================
""",
                item.getId(),
                item.getName(),
                throwable.getMessage());
    }

    @Override
    public void onSkipInWrite(Product item, Throwable throwable) {

        log.error("""
                
================== WRITE FAILED ==================
Product Id   : {}
Product Name : {}
Reason       : {}
==================================================
""",
                item.getId(),
                item.getName(),
                throwable.getMessage());
    }
}
