package com.ms.service.controller;

import com.ms.service.dto.ServiceDTO;
import com.ms.service.exceptions.ServiceException;
import com.ms.service.service.ServiceServices;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceServices serviceServices;

    @Autowired
    public ServiceController(ServiceServices serviceServices){
        this.serviceServices = serviceServices;
    }

    @GetMapping
    public ResponseEntity<List<ServiceDTO>> findAllServices(){
        try{
            List<ServiceDTO> serviceDTOS = service.findAll();
            if (serviceDTOS.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(serviceDTOS);
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional
    @PostMapping
    public ResponseEntity<ServiceDTO> createService(@RequestBody @Valid ServiceDTO serviceDTO){
        try {
            ServiceDTO createdService = service.create(serviceDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdService);
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // testFindById(),
    @GetMapping(value = "/getId/{id}")
    public ResponseEntity<ServiceDTO> findById(@PathVariable String id){
        try {
            ServiceDTO serviceDTO = service.findById(id);
            return ResponseEntity.ok(serviceDTO);
        } catch (ServiceException e){
            return ResponseEntity.notFound().build();
        }
    }

    // testFindByName(),
    @GetMapping(value = "/getName/{name}")
    public ResponseEntity<ServiceDTO> findByName(@PathVariable String name){
        try {
            ServiceDTO serviceDTO = service.findByName(name);
            if (serviceDTO != null) {
                return ResponseEntity.ok(serviceDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ServiceException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // testUpdateService(),
    @Transactional
    @PutMapping(value = "/{id}")
    public ResponseEntity<ServiceDTO> updateService(@PathVariable String id, @Valid @RequestBody ServiceDTO serviceDTO){
        try{
            ServiceDTO updatedService = service.update(id, serviceDTO);
            return ResponseEntity.ok(updatedService);
        } catch (ServiceException e){
            return ResponseEntity.notFound().build();
        }
    }

    // testDeleteService()
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        try {
            service.delete(id);
            return ResponseEntity.ok().build();
        } catch (ServiceException e){
            return ResponseEntity.notFound().build();
        }
    }
}
