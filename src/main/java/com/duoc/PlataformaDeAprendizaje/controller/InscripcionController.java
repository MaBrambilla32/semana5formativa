package com.duoc.PlataformaDeAprendizaje.controller;

import com.duoc.PlataformaDeAprendizaje.service.S3Service;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private final S3Service s3Service;

    // Inyectamos el servicio de S3 que acabas de crear
    public InscripcionController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    /**
     * 1. POST: Subir archivo del resumen de inscripción
     * Requerimiento: Guardar en una carpeta con el número del resumen.
     */
    @PostMapping("/{numeroResumen}/resumen")
    public ResponseEntity<String> subirResumen(
            @PathVariable String numeroResumen,
            @RequestParam("archivo") MultipartFile archivo) {
        try {
            String rutaS3 = s3Service.subirArchivo(numeroResumen, archivo);
            return ResponseEntity.ok("Archivo subido exitosamente a S3. Ruta: " + rutaS3);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error al subir archivo: " + e.getMessage());
        }
    }

    /**
     * 2. GET: Descargar el archivo del resumen
     */
    @GetMapping("/{numeroResumen}/resumen")
    public ResponseEntity<Resource> descargarResumen(
            @PathVariable String numeroResumen,
            @RequestParam("nombreArchivo") String nombreArchivo) {
        
        byte[] data = s3Service.descargarArchivo(numeroResumen, nombreArchivo);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .contentLength(data.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nombreArchivo + "\"")
                .body(resource);
    }

    /**
     * 3. PUT: Modificar / Reemplazar el archivo en caso de error
     */
    @PutMapping("/{numeroResumen}/resumen")
    public ResponseEntity<String> modificarResumen(
            @PathVariable String numeroResumen,
            @RequestParam("archivo") MultipartFile archivo) {
        try {
            // S3 pisa automáticamente el archivo si usa la misma ruta (key)
            String rutaS3 = s3Service.subirArchivo(numeroResumen, archivo);
            return ResponseEntity.ok("Archivo modificado/reemplazado exitosamente en S3. Ruta: " + rutaS3);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error al modificar archivo: " + e.getMessage());
        }
    }

    /**
     * 4. DELETE: Borrar el archivo del resumen de la inscripción
     */
    @DeleteMapping("/{numeroResumen}/resumen")
    public ResponseEntity<String> eliminarResumen(
            @PathVariable String numeroResumen,
            @RequestParam("nombreArchivo") String nombreArchivo) {
        
        s3Service.eliminarArchivo(numeroResumen, nombreArchivo);
        return ResponseEntity.ok("Archivo eliminado correctamente de la carpeta resumen: " + numeroResumen);
    }
} 