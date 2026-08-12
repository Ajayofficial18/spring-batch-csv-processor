package com.batch_csv_processor.processor;

import com.batch_csv_processor.dto.ProductCsv;
import com.batch_csv_processor.entity.Product;
import com.batch_csv_processor.validator.ProductValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductProcessorTest {

    private ProductProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new ProductProcessor(new ProductValidator());
    }

    private ProductCsv validProduct() {

        ProductCsv csv = new ProductCsv();

        csv.setId("P101");
        csv.setName("Laptop");
        csv.setPrice("55000");
        csv.setQuantity("20");
        csv.setDiscount("10");

        return csv;
    }

    @Test
    void shouldConvertCsvToProduct() {

        ProductCsv csv = validProduct();

        Product product = processor.process(csv);

        assertNotNull(product);

        assertEquals("P101", product.getId());

        assertEquals("Laptop", product.getName());

        assertEquals(new BigDecimal("55000.00"), product.getPrice());

        assertEquals(20, product.getQuantity());

        assertEquals(new BigDecimal("10.00"), product.getDiscount());
    }
}