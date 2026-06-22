package com.duoc.PlataformaDeAprendizaje.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    // Inyectamos el cliente de S3 que configuramos en el AwsS3Config
    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    /**
     * 1. SUBIR / MODIFICAR ARCHIVO
     * Guarda el archivo dentro de una carpeta con el número del resumen.
     * Si el archivo ya existe, S3 lo sobrescribe automáticamente (sirve para Modificar).
     */
    public String subirArchivo(String numeroResumen, MultipartFile archivo) throws IOException {
        // Regla del caso: La carpeta debe ser el número del resumen
        String key = numeroResumen + "/" + archivo.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(archivo.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, 
                RequestBody.fromInputStream(archivo.getInputStream(), archivo.getSize()));
        
        return key;
    }

    /**
     * 2. DESCARGAR ARCHIVO
     * Obtiene los bytes del archivo desde el bucket de S3 usando su ruta (key).
     */
    public byte[] descargarArchivo(String numeroResumen, String nombreArchivo) {
        String key = numeroResumen + "/" + nombreArchivo;

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);
        return objectBytes.asByteArray();
    }

    /**
     * 3. ELIMINAR ARCHIVO
     * Borra el archivo físico especificado dentro de la carpeta del resumen.
     */
    public void eliminarArchivo(String numeroResumen, String nombreArchivo) {
        String key = numeroResumen + "/" + nombreArchivo;

        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }
}