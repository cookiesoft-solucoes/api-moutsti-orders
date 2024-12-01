package br.com.alysonrodrigo.apimoutstiorders.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RepProductDTO implements Serializable {

    @NotNull(message = "O ID é obrigatório.")
    private Long id;

    @NotBlank(message = "O nome do produto é obrigatório.")
    @Size(max = 100, message = "O tipo nome do produto não pode exceder 100 caracteres.")
    private String name;

    @DecimalMin(value = "0.0", inclusive = true, message = "O preço não pode ser negativo.")
    @Digits(integer = 3, fraction = 2, message = "A preço deve ter no máximo 3 dígitos inteiros e 2 decimais.")
    private BigDecimal price;

    @NotNull(message = "A quantidade é obrigatória.")
    private Integer quantity;

    @NotNull(message = "O ID da categoria é obrigatório.")
    private Long categoryId;
}
