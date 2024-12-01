package br.com.alysonrodrigo.apimoutstiorders.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaxDTO {

    private Long id;

    @NotBlank(message = "O tipo do imposto é obrigatório.")
    @Size(max = 100, message = "O tipo do imposto não pode exceder 100 caracteres.")
    private String taxType;

    @NotNull(message = "A alíquota do imposto é obrigatória.")
    @DecimalMin(value = "0.0", inclusive = true, message = "A alíquota não pode ser negativa.")
    @Digits(integer = 3, fraction = 2, message = "A alíquota deve ter no máximo 3 dígitos inteiros e 2 decimais.")
    private BigDecimal rate;

    @Size(max = 255, message = "A descrição não pode exceder 255 caracteres.")
    private String description;

    @NotNull(message = "O ID da categoria é obrigatório.")
    private Long categoryId;
}
