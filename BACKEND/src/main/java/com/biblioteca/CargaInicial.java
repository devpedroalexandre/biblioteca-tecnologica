package com.biblioteca;

import com.biblioteca.entity.Livro;
import com.biblioteca.repository.LivroRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CargaInicial implements CommandLineRunner {

    private final LivroRepository livroRepository;

    public CargaInicial(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    @Override
    public void run(String... args) {
        if (livroRepository.count() == 0) {
            livroRepository.save(new Livro("O Senhor dos Anéis", true, "aneis"));
            livroRepository.save(new Livro("Harry Potter", false, "harry"));
            livroRepository.save(new Livro("Dom Casmurro", true, "dom"));
            livroRepository.save(new Livro("1984", false, "orwel"));
        }
    }
}
