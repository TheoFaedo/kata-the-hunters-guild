package com.theofaedo.huntersguild;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

import com.theofaedo.huntersguild.controller.HunterController;
import com.theofaedo.huntersguild.dto.CreateHunterDto;
import com.theofaedo.huntersguild.dto.HunterDto;
import com.theofaedo.huntersguild.repository.HunterRepository;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@TestConstructor(autowireMode = AutowireMode.ALL)
@RequiredArgsConstructor
public class HunterCreationIT {

    private final HunterController hunterController;

    private final HunterRepository hunterRepository;

    @Test
    void givenHunterCorrectName_whenCreateHunter_thenNewHunterPersisted() {
        CreateHunterDto dto = new CreateHunterDto("James");

        HunterDto response = hunterController.createHunter(dto);

        assertEquals("James", response.name());
        assertEquals(1, hunterRepository.count());
    }

}
