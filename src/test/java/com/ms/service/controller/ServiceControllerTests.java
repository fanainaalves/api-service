package com.ms.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.service.dto.ServiceDTO;
import com.ms.service.service.ServiceServices;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;
import java.util.Collections;

//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ServiceController.class)
@ActiveProfiles("tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceControllerTests {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ServiceServices serviceServices;

    @MockBean
    private UploadController uploadController;

    private String id = "65f4826c5488840d27e9868e";
//    private String name = "empresa1";
    private String idCategory = "idCategory";

    @Test
    @Order(1)
    public void testCreate() throws Exception {
        log.info("testCreateService");
        ServiceDTO serviceDTO = new ServiceDTO(
                id, "empresa1", "abc@abc.com", idCategory, true, "",
                new BigDecimal(0.1), 1, 1, "null",
                "null", "test-image.png", "Admin");

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test-image.png",
                MediaType.IMAGE_PNG_VALUE,
                "test image content".getBytes()
        );

        MockMultipartFile service = new MockMultipartFile(
                "service",
                "service.json",
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsBytes(serviceDTO)
        );

        Mockito.when(serviceServices.create(Mockito.any(ServiceDTO.class))).thenReturn(serviceDTO);
        this.mockMvc.perform(multipart("/api/services/create")
                        .file(file)
                        .file(service)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @Order(2)
    public void testFindAll() throws Exception {
        log.info("testFindAllServices");
        ServiceDTO serviceDTO = new ServiceDTO(id, "empresa1", "abc@abc.com", "idCategory", true, "", new BigDecimal(0.1), 1, 1, "null", "null", "image", "Admin");
        Mockito.when(serviceServices.findAll()).thenReturn(Collections.singletonList(serviceDTO));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/services"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @Order(3)
    public void testFindById() throws Exception {
        log.info("testFindById");
        ServiceDTO serviceDTO = new ServiceDTO(id, "empresa1", "abc@abc.com", "idCategory", true, "", new BigDecimal(0.1), 1, 1, "null", "null", "image", "Admin");
        Mockito.when(serviceServices.findById(id)).thenReturn(serviceDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/services/getId/" + id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @Order(4)
    public void testFindByName() throws Exception {
        log.info("testFindByServiceName");
        ServiceDTO serviceDTO = new ServiceDTO(id, "empresa1", "abc@abc.com", "idCategory", true, "", new BigDecimal(0.1), 1, 1, "null", "null", "image", "Admin");
        Mockito.when(serviceServices.findByName("empresa1")).thenReturn(serviceDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/services/getName/empresa1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    @Order(5)
    public void testFindByIdCategory() throws Exception {
        log.info("testFindByIdCategory");
        ServiceDTO serviceDTO = new ServiceDTO(id, "empresa1", "abc@abc.com", "idCategory", true, "", new BigDecimal(0.1), 1, 1, "null", "null", "image", "Admin");
        Mockito.when(serviceServices.findByIdCategory(idCategory)).thenReturn(Collections.singletonList(serviceDTO));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/services/getIdCategory/{idCategory}", idCategory))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[*].idCategory").exists());
    }

    @Test
    @Order(6)
    public void testUpdate() throws Exception {
        log.info("testUpdateService");
        ServiceDTO serviceDTO = new ServiceDTO(
                id, "telecom2", "empresa2@email.com", idCategory, true,
                "descrição aqui...", new BigDecimal(1.2), 1, 1,
                "created", "updated", "updated-image.png", "Admin");

        byte[] fileContent = "updated image content".getBytes();

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "updated-image.png",
                MediaType.IMAGE_PNG_VALUE,
                fileContent
        );

        MockMultipartFile service = new MockMultipartFile(
                "service",
                "service.json",
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsBytes(serviceDTO)
        );

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/api/services/" + id)
                        .file(file)
                        .file(service)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(serviceDTO))
                        .with(request -> {
                            request.setMethod(HttpMethod.PUT.name());
                            return request;
                        }))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Order(7)
    public void testDelete() throws Exception {
        log.info("testDeleteService");
        Mockito.doNothing().when(serviceServices).delete(id);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/services/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
