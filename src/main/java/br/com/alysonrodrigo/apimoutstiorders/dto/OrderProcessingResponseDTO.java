package br.com.alysonrodrigo.apimoutstiorders.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderProcessingResponseDTO {

    private LocalDateTime processingStart;
    private String status;
    private String message;
}
