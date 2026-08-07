package com.batch_csv_processor.validator;

import com.batch_csv_processor.dto.ProductCsv;
import com.batch_csv_processor.exception.InvalidPriceException;
import com.batch_csv_processor.exception.InvalidProductException;
import com.batch_csv_processor.exception.InvalidQuantityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class ProductValidator {

    public void validate(ProductCsv csv) {

        validateId(csv);

        validateName(csv);

        validatePrice(csv);

        validateQuantity(csv);

        validateDiscount(csv);
    }

    private void validateDiscount(ProductCsv csv) {

        if (csv.getDiscount() == null || csv.getDiscount().isBlank()) {

            log.error("Discount can not be null or blank");

            throw new InvalidProductException("Discount can not be null or blank");
        }
    }

    private void validateName(ProductCsv csv) {

        if (csv.getName() == null || csv.getName().isBlank()) {

            log.error("Invalid Product Name : {}", csv.getId());

            throw new InvalidProductException("Product Name cannot be empty");
        }
    }

    private void validatePrice(ProductCsv csv) {

        try {

            BigDecimal price = new BigDecimal(csv.getPrice());

            if (price.compareTo(BigDecimal.ZERO) <= 0) {

                log.error(
                        "Skipping Product. Id={}, Name={}, Reason={}",
                        csv.getId(),
                        csv.getName(),
                        "Invalid Price"
                );

                throw new InvalidPriceException("Price must be greater than zero");
            }

        } catch (NumberFormatException ex) {

            throw new InvalidPriceException("Price is not numeric");
        }
    }

    private void validateQuantity(ProductCsv csv) {

        try {

            int quantity = Integer.parseInt(csv.getQuantity());

            if (quantity < 0) {

                log.error("Invalid Quantity : {}", csv.getId());

                throw new InvalidQuantityException("Quantity cannot be negative");
            }

        } catch (NumberFormatException ex) {

            throw new InvalidQuantityException("Quantity is not numeric");
        }
    }
    private void validateId(ProductCsv csv) {

        if (csv.getId() == null || csv.getId().isBlank()) {

            log.error("Invalid Product Id");

            throw new InvalidProductException("Product Id cannot be empty");
        }
    }

}
