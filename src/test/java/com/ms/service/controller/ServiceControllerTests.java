package com.ms.service.controller;

import com.ms.service.ServiceApplicationTests;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceControllerTests extends ServiceApplicationTests {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private MockMvc mockMvc;
    private String id;


}
