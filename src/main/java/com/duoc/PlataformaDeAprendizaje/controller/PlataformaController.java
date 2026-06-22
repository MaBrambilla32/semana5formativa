package com.duoc.PlataformaDeAprendizaje.controller;

import com.duoc.PlataformaDeAprendizaje.model.Curso;
import com.duoc.PlataformaDeAprendizaje.repository.CursoRepository;
import com.duoc.PlataformaDeAprendizaje.repository.InscripcionRepository;
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

    @GetMapping("/cursos")
    public List<Curso> obtenerCursos() {
        return cursoRepository.findAll();
    }
    @PostMapping("/cursos")
    public Curso crearCurso(@RequestBody Curso curso) {
    return cursoRepository.save(curso);
    }
 }