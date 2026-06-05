package com.cristmerc.biblioteca_api.dto;

    public class EstadisticasDTO {
        private long totalLibros;
        private int anioPromedio;
        private String autorMasPopular;

        public EstadisticasDTO(long totalLibros, int anioPromedio, String autorMasPopular) {
            this.totalLibros = totalLibros;
            this.anioPromedio = anioPromedio;
            this.autorMasPopular = autorMasPopular != null ? autorMasPopular : "N/A";
        }

        // Getters
        public long getTotalLibros() { return totalLibros; }
        public int getAnioPromedio() { return anioPromedio; }
        public String getAutorMasPopular() { return autorMasPopular; }
    }

