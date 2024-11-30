package br.com.alysonrodrigo.apimoutstiorders.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class RepUserDTO implements Serializable {

    private Long id;

    @NotBlank(message = "O nome do usuário é obrigatório.")
    @Size(max = 100, message = "O nome do usuário não pode exceder 255 caracteres.")
    private String name;

    @NotBlank(message = "O email do usuário é obrigatório.")
    @Size(max = 100, message = "O nome do usuário não pode exceder 255 caracteres.")
    private String email;
}
