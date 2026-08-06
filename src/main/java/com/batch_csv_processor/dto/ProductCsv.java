package com.batch_csv_processor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCsv {

    private String id;
    private String name;
    private String price;
    private String quantity;
    private String discount;

}
