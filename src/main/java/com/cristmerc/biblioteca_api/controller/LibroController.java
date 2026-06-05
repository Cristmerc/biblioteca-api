package com.cristmerc.biblioteca_api.controller;

import com.cristmerc.biblioteca_api.entity.Libro;
import com.cristmerc.biblioteca_api.repository.LibroRepository;
import org.springframework.web.bind.annotation.*;
import com.cristmerc.biblioteca_api.dto.EstadisticasDTO;

import java.util.List;

@RestController
@RequestMapping("/libros")
public class LibroController {

    private final LibroRepository repository;

    public LibroController(LibroRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Libro> getAll(@RequestParam(required = false) String author) {
        if (author != null && !author.trim().isEmpty()) {
            return repository.findByAuthor(author);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Libro getById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow();
    }

    @PostMapping
    public Libro create(@RequestBody Libro book) {
        return repository.save(book);
    }

    @PutMapping("/{id}")
    public Libro update(@PathVariable Long id,
                        @RequestBody Libro updatedBook) {

        Libro book = repository.findById(id).orElseThrow();

        book.setTitulo(updatedBook.getTitulo());
        book.setAutor(updatedBook.getAutor());
        book.setAnio(updatedBook.getAnio());

        return repository.save(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping("/estadisticas")
    public EstadisticasDTO getStatistics() {
        long total = repository.count();
        Double rawAverage = repository.getAverageYear(); // Usando el nuevo método del repositorio
        int average = (rawAverage != null) ? rawAverage.intValue() : 0;
        String author = repository.getMostPopularAuthor(); // Usando el nuevo método del repositorio

        return new EstadisticasDTO(total, average, author);
    }

}
