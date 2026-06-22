package com.duoc.PlataformaDeAprendizaje.controller;

import com.duoc.PlataformaDeAprendizaje.model.Curso;
import com.duoc.PlataformaDeAprendizaje.model.Guia;
import com.duoc.PlataformaDeAprendizaje.repository.CursoRepository;
import com.duoc.PlataformaDeAprendizaje.repository.InscripcionRepository;
import com.duoc.PlataformaDeAprendizaje.repository.GuiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PlataformaController {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private InscripcionRepository inscripcionRepository;

    // Inyectamos el repositorio de las guías
    @Autowired
    private GuiaRepository guiaRepository;

    // --- CRUD DE CURSOS ---
    @GetMapping("/cursos")
    public List<Curso> obtenerCursos() {
        return cursoRepository.findAll();
    }

    @PostMapping("/cursos")
    public Curso crearCurso(@RequestBody Curso curso) {
        return cursoRepository.save(curso);
    }

    // --- CRUD DE GUÍAS (EL DEFINITIVO) ---

    // 1. LEER TODAS LAS GUÍAS (GET)
    @GetMapping("/guias")
    public List<Guia> obtenerGuias() {
        return guiaRepository.findAll();
    }

    // 2. CREAR UNA GUÍA NUEVA (POST)
    @PostMapping("/guias")
    public Guia crearGuia(@RequestBody Guia guia) {
        return guiaRepository.save(guia);
    }

    // 3. ACTUALIZAR UNA GUÍA EXISTENTE (PUT)
    @PutMapping("/guias/{id}")
    public Guia actualizarGuia(@PathVariable Long id, @RequestBody Guia guiaActualizada) {
        // Le seteamos el ID para que Spring sepa que debe sobreescribir y no crear una nueva
        guiaActualizada.setId(id);
        return guiaRepository.save(guiaActualizada);
    }

    // 4. ELIMINAR UNA GUÍA (DELETE)
    @DeleteMapping("/guias/{id}")
    public void eliminarGuia(@PathVariable Long id) {
        guiaRepository.deleteById(id);
    }
} 