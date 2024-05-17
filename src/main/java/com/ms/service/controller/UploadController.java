package com.ms.service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping(value = "/api/upload", produces = {"application/json"})
@Slf4j
@CrossOrigin
public class UploadController {
    private final String pathFiles;

    @Autowired
    public UploadController(@Value("${app.path.files}") String pathFiles){
        this.pathFiles = pathFiles;
    }

    @PostMapping("/file")
    public ResponseEntity<String> saveFile(@RequestParam("file") MultipartFile file) {
        log.info("Recebendo o arquivo: ", file.getOriginalFilename());
        var path = pathFiles + file.getOriginalFilename();
        log.info("Novo nome do arquivo: " + path);

        try {
            Files.copy(file.getInputStream(), Path.of(path), StandardCopyOption.REPLACE_EXISTING);
            return new ResponseEntity<>("{ \"mensagem\": \"Arquivo carregado com sucesso!\"}", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Erro ao processar arquivo", e);
            return new ResponseEntity<>("{ \"mensagem\": \"Erro ao carregar o arquivo!\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String extractExtension(String nameFiles) {
        int i = nameFiles.lastIndexOf(".");
        return nameFiles.substring(i + 1);
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
