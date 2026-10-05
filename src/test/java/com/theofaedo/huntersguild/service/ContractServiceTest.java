package com.theofaedo.huntersguild.service;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.theofaedo.huntersguild.entity.ContractEntity;
import com.theofaedo.huntersguild.entity.ContractStatus;
import com.theofaedo.huntersguild.entity.HunterEntity;
import com.theofaedo.huntersguild.repository.ContractRepository;
import com.theofaedo.huntersguild.repository.HunterRepository;

@ExtendWith(MockitoExtension.class)
class ContractServiceTest {

    @Mock
    HunterRepository hunterRepository;

    @Mock
    ContractRepository contractRepository;

    @InjectMocks
    ContractService contractService;

    private static final Set<ContractEntity> CONTRACTS = Set.of(
            ContractEntity.createNew("test", "test", 1, 10, ContractStatus.COMPLETED),
            ContractEntity.createNew("test2", "test", 1, 20, ContractStatus.COMPLETED),
            ContractEntity.createNew("test3", "test", 1, 30, ContractStatus.COMPLETED));

    @Test
    void givenValidHunterName_whenCreateHunter_thenShouldPersistAndReturnTheCreated() {

    }

    @Test
    void givenHunterWhoCompletedMultipleContracts_when() {

    }

    // accepting a contract;
    // rejecting acceptance when the hunter level is too low;
    // rejecting acceptance when the contract is already accepted;
    // completing a contract;
    // granting the reward;
    // preventing the same contract from being completed twice;
    // preventing a hunter from completing someone else's contract.

    private HunterEntity createHunterWithContracts() {
        HunterEntity hunter = HunterEntity.createNew("James");

        CONTRACTS.forEach(hunter::addContract);

        return hunter;
    }

}
