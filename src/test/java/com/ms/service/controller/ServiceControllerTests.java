package com.ms.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.service.ServiceApplicationTests;
import com.ms.service.dto.ServiceDTO;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceControllerTests extends ServiceApplicationTests {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private MockMvc mockMvc;
    private String id;
    private String name;

    @Autowired
    private ServiceController serviceController;

    @BeforeEach
    public void setUp(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(serviceController).build();
        this.id = "65f346c11ac3d257aed346f9";
        this.name = "empresa1";
    }

    @Test
    @Order(0)
    public void testCreateService() throws Exception{
        log.info("testCreateService");
        // CONSTRUTOR DE SERVICEDTO = id, nome, email, status, descricao, preco, tempo de execução, prazo
        ServiceDTO serviceDTO = new ServiceDTO(null, "empresa1", "empresa1@email.com");
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/api/services")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(serviceDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andReturn();
    }

    @Test
    @Order(1)
    public void testFindAllServices() throws Exception{
        log.info("testFindAllServices");
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/services"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
    }

    @Test
    @Order(2)
    public void testFindById() throws Exception{
        log.info("testFindById");
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/services/getId/" + id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    @Order(3)
    public void testFindByName() throws Exception{
        log.info("testFindByName");
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/services/getName/" + name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists());
    }

    @Test
    @Order(4)
    public void testUpdateService() throws Exception{
        log.info("testUpdateService");
        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/services/" + id)
                // CONSTRUTOR DE SERVICEDTO = id, nome, email, status, descricao, preco, tempo de execução, prazo
                .content(asJsonString(new ServiceDTO(id, "telecom2", "empresa2@email.com")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    @Order(5)
    public void testDeleteService() throws Exception{
        log.info("testDeleteService");
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/clients/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").doesNotExist());
    }

    public static String asJsonString(final Object object){
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
