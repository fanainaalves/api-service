package com.ms.service.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UploadController.class)
public class UploadControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UploadController uploadController;

    @Test
    public void testSaveFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test-image.png",
                MediaType.IMAGE_PNG_VALUE,
                "test image content".getBytes()
        );

        String fileStorageLocation = "C:/Users/fsilva3/OneDrive - Getronics/Documentos/Angular/Projeto Final/Frontend/ogani-admin/src/assets/img/service/";
        Path path = Paths.get(fileStorageLocation + file.getOriginalFilename());
        Files.createDirectories(path.getParent());

        Map<String, String> response = new HashMap<>();
        response.put("imageUrl", "/api/upload/file/" + file.getOriginalFilename());

        Mockito.when(uploadController.saveFile(Mockito.any())).thenCallRealMethod();

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/upload/file")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.imageUrl").value("/api/upload/file/test-image.png"));
    }
}
