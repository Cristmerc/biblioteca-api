package com.cristmerc.biblioteca_api.repository;

import com.cristmerc.biblioteca_api.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    @Query("SELECT l FROM Libro l WHERE LOWER(l.autor) LIKE LOWER(CONCAT('%', :author, '%'))")
    List<Libro> findByAuthor(@Param("author") String author);

    // 1. Calculates the average publication year
    @Query("SELECT AVG(l.anio) FROM Libro l")
    Double getAverageYear();

    // 2. Finds the author with the most books in the catalog
    @Query("SELECT l.autor FROM Libro l GROUP BY l.autor ORDER BY COUNT(l) DESC LIMIT 1")
    String getMostPopularAuthor();
}
