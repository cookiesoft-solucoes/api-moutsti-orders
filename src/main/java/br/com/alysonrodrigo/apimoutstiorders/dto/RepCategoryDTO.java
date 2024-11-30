package br.com.alysonrodrigo.apimoutstiorders.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RepCategoryDTO implements Serializable {

    @NotNull(message = "O ID é obrigatório.")
    private Long id;

    @NotBlank(message = "O nome da categoria é obrigatório.")
    @Size(max = 100, message = "O nome da categoria não pode exceder 100 caracteres.")
    private String name;
    private String description;
}
