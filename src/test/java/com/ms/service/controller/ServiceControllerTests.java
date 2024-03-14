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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceControllerTests extends ServiceApplicationTests {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private MockMvc mockMvc;
    private String id;

    @Autowired
    private ServiceController serviceController;

    @BeforeEach
    public void setUp(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(serviceController).build();
        this.id = "";
    }

    @Test
    @Order(0)
    public void testCreateService() throws Exception{
        log.info("testCreateService");
        // CONSTRUTOR DE SERVICEDTO = id, nome, email, status, descricao, preco, tempo de execução, prazo
        ServiceDTO serviceDTO = new ServiceDTO(id, "telecom", "empresa1@email.com", true, "empresa de telecom", 10.00, 1, 2);
        this.mockMvc.perform(MockMvcBuilders
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
    public void testFindById() throws Exception{

    }

    @Test
    @Order(2)
    public void testFindByName() throws Exception{

    }

    @Test
    @Order(3)
    public void testUpdateService() throws Exception{

    }

    @Test
    @Order(4)
    public void testDeleteService() throws Exception{

    }

    public static String asJsonString(final Object object){
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
