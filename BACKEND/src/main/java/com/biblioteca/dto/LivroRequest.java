package com.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LivroRequest(

        @NotBlank(message = "O título é obrigatório")
        @Size(max = 100, message = "O título pode ter no máximo 100 caracteres")
        String titulo,

        @NotNull(message = "A disponibilidade é obrigatória")
        Boolean disponivel,

        @NotBlank(message = "A capa é obrigatória")
        @Pattern(regexp = "aneis|harry|dom|orwel|principe",
                message = "Capa inválida. Use: aneis, harry, dom, orwel ou principe")
        String capa
) {
}
