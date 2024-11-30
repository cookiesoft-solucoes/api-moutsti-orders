package br.com.alysonrodrigo.apimoutstiorders.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {

    private String code;
    private Integer quantity;
    private LocalDateTime date;
    private BigDecimal total;
    private BigDecimal totalTax;
    private String clientName;
    private String status;
    private List<ItemOrderDTO> items;
}
