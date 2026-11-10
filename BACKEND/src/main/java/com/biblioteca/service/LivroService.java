package com.biblioteca.service;

import com.biblioteca.dto.LivroDTO;
import com.biblioteca.dto.LivroRequest;
import com.biblioteca.entity.Livro;
import com.biblioteca.exception.LivroNaoEncontradoException;
import com.biblioteca.mapper.LivroMapper;
import com.biblioteca.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public List<LivroDTO> listarTodos() {
        return livroRepository.findAll().stream()
                .map(LivroMapper::toDTO)
                .toList();
    }

    public LivroDTO buscarPorId(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new LivroNaoEncontradoException(id));
        return LivroMapper.toDTO(livro);
    }

    public LivroDTO cadastrar(LivroRequest request) {
        Livro livro = LivroMapper.toEntity(request);
        Livro salvo = livroRepository.save(livro);
        return LivroMapper.toDTO(salvo);
    }
}
