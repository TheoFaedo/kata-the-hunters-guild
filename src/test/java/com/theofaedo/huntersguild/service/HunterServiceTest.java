package com.theofaedo.huntersguild.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.theofaedo.huntersguild.dto.HunterStatsDto;
import com.theofaedo.huntersguild.entity.ContractEntity;
import com.theofaedo.huntersguild.entity.ContractStatus;
import com.theofaedo.huntersguild.entity.HunterEntity;
import com.theofaedo.huntersguild.repository.HunterRepository;

@ExtendWith(MockitoExtension.class)
class HunterServiceTest {

    @Mock
    HunterRepository hunterRepository;

    @InjectMocks
    HunterService hunterService;

    private static final Set<ContractEntity> CONTRACTS = Set.of(
            ContractEntity.createNew("test", "test", 1, 10, ContractStatus.COMPLETED),
            ContractEntity.createNew("test2", "test", 1, 20, ContractStatus.COMPLETED),
            ContractEntity.createNew("test3", "test", 1, 30, ContractStatus.COMPLETED));

    @Test
    void givenValidHunterName_whenCreateHunter_thenShouldPersistAndReturnTheCreated() {
        final String name = "James";

        when(hunterRepository.save(any())).thenAnswer(args -> args.getArgument(0));
        HunterEntity hunter = hunterService.createHunter(name);

        assertEquals(name, hunter.getName());
    }

    @Test
    void givenHunterWhoCompletedMultipleContracts_when() {
        final HunterEntity hunter = createHunterWithContracts();

        when(hunterRepository.findById(hunter.getId()))
                .thenReturn(Optional.of(hunter));

        HunterStatsDto dto = hunterService.findStats(hunter.getId());

        assertEquals(3, dto.completedContracts());
        assertEquals(60, dto.totalGoldEarned());

    }

    private HunterEntity createHunterWithContracts() {
        HunterEntity hunter = HunterEntity.createNew("James");

        CONTRACTS.forEach(hunter::addContract);

        return hunter;
    }

}
