package br.com.alysonrodrigo.apimoutstiorders.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderCreateDTO {

    @NotBlank(message = "O código do pedido é obrigatório.")
    @Size(max = 50, message = "O código do pedido não pode exceder 50 caracteres.")
    private String code;

    @NotNull(message = "O ID do cliente é obrigatório.")
    private Long clientId;

    @NotEmpty(message = "A lista de itens do pedido não pode estar vazia.")
    private List<ItemOrderDTO> items;

    @Data
    public static class ItemOrderDTO {

        @NotNull(message = "A quantidade do item é obrigatória.")
        @Positive(message = "A quantidade do item deve ser maior que zero.")
        private Integer quantity;

        @NotNull(message = "O preço do item é obrigatório.")
        @DecimalMin(value = "0.0", inclusive = false, message = "O preço do item deve ser maior que zero.")
        private BigDecimal price;

        @NotNull(message = "O ID do produto é obrigatório.")
        private Long productId;

    }
}
