package com.duoc.PlataformaDeAprendizaje.model;

import jakarta.persistence.*;

@Entity
public class Inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String estudiante;
    private String cursosSeleccionados;
    private Double totalAPagar;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEstudiante() { return estudiante; }
    public void setEstudiante(String estudiante) { this.estudiante = estudiante; }
    public String getCursosSeleccionados() { return cursosSeleccionados; }
    public void setCursosSeleccionados(String cursosSeleccionados) { this.cursosSeleccionados = cursosSeleccionados; }
    public Double getTotalAPagar() { return totalAPagar; }
    public void setTotalAPagar(Double totalAPagar) { this.totalAPagar = totalAPagar; }
}  