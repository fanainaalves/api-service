package com.ms.service.controller;

import com.ms.service.dto.ServiceDTO;
import com.ms.service.exceptions.ServiceException;
import com.ms.service.exceptions.ServiceNotFoundException;
import com.ms.service.service.ServiceServices;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceServices serviceServices;

    @Autowired
    public ServiceController(ServiceServices serviceServices){
        this.serviceServices = serviceServices;
    }

    @GetMapping
    public ResponseEntity<List<ServiceDTO>> findAll(){
        try{
            List<ServiceDTO> serviceDTOS = serviceServices.findAll();
            if (serviceDTOS.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(serviceDTOS);
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ServiceDTO> create(@RequestParam("file") MultipartFile file, @RequestPart("service") @Valid ServiceDTO serviceDTO) {
        try {
            String image = serviceServices.saveImage(file);
            serviceDTO.setImage(image);

            ServiceDTO createdService = serviceServices.create(serviceDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdService);
        } catch (ServiceException | IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/getId/{id}")
    public ResponseEntity<ServiceDTO> findById(@PathVariable String id){
        try {
            ServiceDTO serviceDTO = serviceServices.findById(id);
            return ResponseEntity.ok(serviceDTO);
        } catch (ServiceException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/getName/{name}")
    public ResponseEntity<ServiceDTO> findByName(@PathVariable String name){
        try {
            ServiceDTO serviceDTO = serviceServices.findByName(name);
            if (serviceDTO != null) {
                return ResponseEntity.ok(serviceDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/getIdCategory/{idCategory}")
    public ResponseEntity<List<ServiceDTO>> findByIdCategory(@PathVariable String idCategory){
        try {
            List<ServiceDTO> serviceDTO = serviceServices.findByIdCategory(idCategory);
            if(serviceDTO.isEmpty()){
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(serviceDTO);
            }
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ServiceDTO> update(@PathVariable String id, @RequestPart(value = "file",
            required = false) MultipartFile file, @RequestPart("service") @Valid ServiceDTO serviceDTO) {
        try{
            if (file != null && !file.isEmpty()) {
                String image = serviceServices.saveImage(file);
                serviceDTO.setImage(image);
            }
            ServiceDTO updatedService = serviceServices.update(id, serviceDTO);
            return ResponseEntity.ok(updatedService);
        } catch (ServiceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ServiceException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        try {
            serviceServices.delete(id);
            return ResponseEntity.ok().build();
        } catch (ServiceException e){
            return ResponseEntity.notFound().build();
        }
    }
}
