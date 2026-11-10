package com.biblioteca.mapper;

import com.biblioteca.dto.LivroDTO;
import com.biblioteca.dto.LivroRequest;
import com.biblioteca.entity.Livro;

public class LivroMapper {

    public static Livro toEntity(LivroRequest request) {
        return new Livro(request.titulo(), request.disponivel(), request.capa());
    }

    public static LivroDTO toDTO(Livro livro) {
        return new LivroDTO(livro.getId(), livro.getTitulo(), livro.getDisponivel(), livro.getCapa());
    }
}
