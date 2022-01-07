package org.femelloffm.dataanalysis.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Path;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = HealthCheckController.class)
public class HealthCheckControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean(name = "inputPath")
    private Path inputPath;
    @MockBean(name = "outputPath")
    private Path outputPath;

    @Test
    public void shouldRespondWithStatusOkAndPathInformation() throws Exception {
        when(inputPath.toString()).thenReturn("/input");
        when(outputPath.toString()).thenReturn("/output");
        String expectedString = "{\"input_path\": \"/input\", \"output_path\": \"/output\"}";
        this.mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(expectedString));
    }
}