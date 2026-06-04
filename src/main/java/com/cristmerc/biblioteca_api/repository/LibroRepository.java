package com.cristmerc.biblioteca_api.repository;

import com.cristmerc.biblioteca_api.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Long> {
}
