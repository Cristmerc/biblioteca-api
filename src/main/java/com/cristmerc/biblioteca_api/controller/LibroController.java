package com.cristmerc.biblioteca_api.controller;

import com.cristmerc.biblioteca_api.entity.Libro;
import com.cristmerc.biblioteca_api.repository.LibroRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libros")
public class LibroController {

    private final LibroRepository repository;

    public LibroController(LibroRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Libro> obtenerTodos() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Libro obtenerPorId(@PathVariable Long id) {
        return repository.findById(id).orElseThrow();
    }

    @PostMapping
    public Libro crear(@RequestBody Libro libro) {
        return repository.save(libro);
    }

    @PutMapping("/{id}")
    public Libro actualizar(@PathVariable Long id,
                            @RequestBody Libro libroActualizado) {

        Libro libro = repository.findById(id).orElseThrow();

        libro.setTitulo(libroActualizado.getTitulo());
        libro.setAutor(libroActualizado.getAutor());
        libro.setAnio(libroActualizado.getAnio());

        return repository.save(libro);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repository.deleteById(id);
    }
}