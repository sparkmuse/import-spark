package com.github.sparkmuse.spark.controller;

import com.github.sparkmuse.spark.model.ImportJob;
import com.github.sparkmuse.spark.service.ImportService;
import com.github.sparkmuse.spark.service.exception.JobAlreadyRunningException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.sparkmuse.spark.model.ImportJob.Status.RUNNING;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ImportJobController.class)
class ImportJobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImportService importService;

    @Test
    @DisplayName("returns 201")
    void returns201() throws Exception {

        ImportJob importJob = ImportJob.builder().status(RUNNING).build();
        when(importService.process()).thenReturn(importJob);

        mockMvc.perform(post("/importjobs"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(importJob.getId()))
                .andExpect(jsonPath("$.status").value(importJob.getStatus().toString()));
    }

    @Test
    @DisplayName("returns 400 when there is another request")
    void returns400() throws Exception {

        when(importService.process()).thenThrow(JobAlreadyRunningException.class);

        mockMvc.perform(post("/importjobs"))
                .andExpect(status().isBadRequest());
    }
}