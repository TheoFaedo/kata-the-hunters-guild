package com.theofaedo.huntersguild.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.theofaedo.huntersguild.mapper.HunterMapper;
import com.theofaedo.huntersguild.service.HunterService;

@WebMvcTest(HunterController.class)
public class HunterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HunterService hunterService;

    @MockitoBean
    private HunterMapper hunterMapper;

    @Test
    void givenInvalidName_whenPostHunter_thenBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/hunters")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": ""
                        }
                        """))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
