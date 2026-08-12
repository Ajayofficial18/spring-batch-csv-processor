package com.batch_csv_processor.validator;

import com.batch_csv_processor.dto.ProductCsv;
import com.batch_csv_processor.exception.InvalidDiscountException;
import com.batch_csv_processor.exception.InvalidPriceException;
import com.batch_csv_processor.exception.InvalidProductException;
import com.batch_csv_processor.exception.InvalidQuantityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductValidatorTest {

    private ProductValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ProductValidator();
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
    void shouldValidateValidProduct() {
        ProductCsv csv = validProduct();
        assertDoesNotThrow(() -> validator.validate(csv));
    }

    @Test
    void shouldThrowWhenIdIsBlank() {
        ProductCsv csv = validProduct();
        csv.setId("");
        InvalidProductException exception = assertThrows(
                InvalidProductException.class,
                () -> validator.validate(csv)
        );
        assertEquals(
                "Product Id cannot be empty",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowWhenNameIsBlank() {
        ProductCsv csv = validProduct();
        csv.setName("");
        InvalidProductException exception = assertThrows(
                InvalidProductException.class,
                () -> validator.validate(csv)
        );
        assertEquals(
                "Product Name cannot be empty",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowWhenPriceIsBlank() {
        ProductCsv csv = validProduct();
        csv.setPrice("");
        InvalidPriceException exception = assertThrows(
                InvalidPriceException.class,
                () -> validator.validate(csv)
        );
        assertEquals(
                "Price is not numeric",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowWhenPriceIsNegative() {
        ProductCsv csv = validProduct();
        csv.setPrice("-526");
        InvalidPriceException exception = assertThrows(
                InvalidPriceException.class,
                () -> validator.validate(csv)
        );
        assertEquals(
                "Price must be greater than zero",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowWhenQuantityIsNegative(){
        ProductCsv csv = validProduct();
        csv.setQuantity("-1");
        InvalidQuantityException exception = assertThrows(
                InvalidQuantityException.class,
                () -> validator.validate(csv)
        );
        assertEquals("Quantity cannot be negative",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowWhenQuantityIsNotNumeric(){
        ProductCsv csv = validProduct();
        csv.setQuantity("dv446");
        InvalidQuantityException exception = assertThrows(
                InvalidQuantityException.class,
                () -> validator.validate(csv)
        );
        assertEquals("Quantity is not numeric",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowWhenDiscountIsNull(){
        ProductCsv csv = validProduct();
        csv.setDiscount(null);
        InvalidDiscountException exception = assertThrows(
                InvalidDiscountException.class,
                ()-> validator.validate(csv)
        );
        assertEquals("Discount cannot be null",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowWhenDiscountIsBlank(){
        ProductCsv csv = validProduct();
        csv.setDiscount("");
        InvalidDiscountException exception = assertThrows(
                InvalidDiscountException.class,
                ()-> validator.validate(csv)
        );
        assertEquals("Discount is not numeric",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowWhenDiscountIsNegative(){
        ProductCsv csv = validProduct();
        csv.setDiscount("-5");
        InvalidDiscountException exception = assertThrows(
                InvalidDiscountException.class,
                ()-> validator.validate(csv)
        );
        assertEquals("Discount cannot be negative",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowWhenDiscountIsNotNumeric(){
        ProductCsv csv = validProduct();
        csv.setDiscount("abc");
        InvalidDiscountException exception = assertThrows(
                InvalidDiscountException.class,
                ()-> validator.validate(csv)
        );
        assertEquals("Discount is not numeric",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowWhenDiscountIsGreaterThanHundred() {
        ProductCsv csv = validProduct();
        csv.setDiscount("150");
        InvalidDiscountException exception = assertThrows(
                InvalidDiscountException.class,
                () -> validator.validate(csv)
        );
        assertEquals(
                "Discount cannot be greater than 100",
                exception.getMessage()
        );
    }

}