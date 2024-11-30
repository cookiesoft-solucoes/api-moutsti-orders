package br.com.alysonrodrigo.apimoutstiorders.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ItemOrderDTO {

    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private String taxType;
}
