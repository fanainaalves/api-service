package com.ms.service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/upload", produces = {"application/json"})
@Slf4j
@CrossOrigin("*")
public class UploadController {

    private final String pathFiles;

    public UploadController(@Value("${app.path.files}") String pathFiles){
        this.pathFiles = pathFiles;
    }

    @PostMapping("/file")
    public ResponseEntity<Map<String, String>> saveFile(@RequestParam("file") MultipartFile file) {
        var fileName = file.getOriginalFilename();
        var path = Path.of(pathFiles + fileName);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            Map<String, String> response = new HashMap<>();
            response.put("imageUrl", "/api/upload/file/" + fileName);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensagem", "Erro ao carregar o arquivo!");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/file/{fileName:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        try {
            Path filePath = Path.of(pathFiles + fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Erro ao buscar arquivo", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
