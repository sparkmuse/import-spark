package com.github.sparkmuse.spark.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DeletionCleanController.class)
class DeletionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("returns 201")
    void returns201() throws Exception {
        mockMvc.perform(post("/deletionclean"))
                .andExpect(status().isCreated());
    }
}
