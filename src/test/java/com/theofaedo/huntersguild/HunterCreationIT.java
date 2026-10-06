package com.theofaedo.huntersguild;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.theofaedo.huntersguild.repository.HunterRepository;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@AutoConfigureMockMvc
@TestConstructor(autowireMode = AutowireMode.ALL)
@RequiredArgsConstructor
public class HunterCreationIT {

    private final MockMvc mockMvc;

    private final HunterRepository hunterRepository;

    @Test
    void givenHunterCorrectName_whenCreateHunter_thenNewHunterPersisted() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/hunters")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "James"
                        }
                        """))
                .andExpect(MockMvcResultMatchers.status().isOk());
        assertEquals(1, hunterRepository.count());
    }

}
