package com.batch_csv_processor.processor;

import com.batch_csv_processor.dto.ProductCsv;
import com.batch_csv_processor.entity.Product;
import com.batch_csv_processor.validator.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
public class ProductProcessor implements ItemProcessor<ProductCsv, Product> {

    private final ProductValidator validator;

    @Override
    public Product process(ProductCsv csv) {

        validator.validate(csv);

        return Product.builder()
                .id(csv.getId().trim())
                .name(csv.getName().trim())
                .price(convertPrice(csv.getPrice()))
                .quantity(convertQuantity(csv.getQuantity()))
                .discount(convertDiscount(csv.getDiscount()))
                .build();
    }

    private BigDecimal convertPrice(String price) {

        return new BigDecimal(price.trim())
                .setScale(2, RoundingMode.HALF_UP);
    }

    private Integer convertQuantity(String quantity) {

        return Integer.parseInt(quantity.trim());
    }

    private BigDecimal convertDiscount(String discount) {

        String value = discount.replace("%", "").trim();

        return new BigDecimal(value)
                .setScale(2, RoundingMode.HALF_UP);
    }

}
