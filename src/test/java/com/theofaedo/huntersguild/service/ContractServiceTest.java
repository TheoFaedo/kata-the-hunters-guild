package com.theofaedo.huntersguild.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.theofaedo.huntersguild.entity.ContractEntity;
import com.theofaedo.huntersguild.entity.ContractStatus;
import com.theofaedo.huntersguild.entity.HunterEntity;
import com.theofaedo.huntersguild.exception.ConflictException;
import com.theofaedo.huntersguild.exception.ForbiddenException;
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
            ContractEntity.createNew("test2", "test", 1, 20, ContractStatus.COMPLETED));

    @Test
    void givenHunterAndAvailableContract_whenAcceptContract_thenContractSavedAsAcceptedAndHunterSavedWithTheContract() {
        HunterEntity hunter = HunterEntity.createNew("James");

        ContractEntity contract = ContractEntity.createNew("mocked", "mocker", 1, 10, ContractStatus.AVAILABLE);

        when(contractRepository.findById(contract.getId())).thenReturn(Optional.of(contract));
        when(hunterRepository.findById(hunter.getId())).thenReturn(Optional.of(hunter));
        when(contractRepository.save(any())).thenAnswer(args -> args.getArgument(0));
        when(hunterRepository.save(any())).thenAnswer(args -> args.getArgument(0));
        ContractEntity saved = contractService.acceptContract(contract.getId(), hunter.getId());

        assertEquals(ContractStatus.ACCEPTED, saved.getStatus());
        assertEquals(1, saved.getHunter().getContracts().size());
    }

    @Test
    void givenHunterAndContractWithTooMuchLevel_whenAcceptContract_thenThrowForbiddenException() {
        HunterEntity hunter = HunterEntity.createNew("James");

        ContractEntity contract = ContractEntity.createNew("mocked", "mocker", 2, 10, ContractStatus.AVAILABLE);

        when(contractRepository.findById(contract.getId())).thenReturn(Optional.of(contract));
        when(hunterRepository.findById(hunter.getId())).thenReturn(Optional.of(hunter));

        assertThrows(ForbiddenException.class,
                () -> contractService.acceptContract(contract.getId(), hunter.getId()));
    }

    @Test
    void givenHunterAndAcceptedContract_whenAcceptContract_thenThrowConflictException() {
        HunterEntity hunter = HunterEntity.createNew("James");

        ContractEntity contract = ContractEntity.createNew("mocked", "mocker", 2, 10, ContractStatus.ACCEPTED);

        when(contractRepository.findById(contract.getId())).thenReturn(Optional.of(contract));
        when(hunterRepository.findById(hunter.getId())).thenReturn(Optional.of(hunter));

        assertThrows(ConflictException.class,
                () -> contractService.acceptContract(contract.getId(), hunter.getId()));
    }

    @Test
    void givenHunterAndAcceptedContract_whenCompleteContract_thenContractSavedAsCompletedAndHunterGoldIncreased() {
        HunterEntity hunter = HunterEntity.createNew("James");

        ContractEntity contract = ContractEntity.createNew("mocked", "mocker", 1, 10, ContractStatus.AVAILABLE);
        contract.accept(hunter);

        when(contractRepository.findById(contract.getId())).thenReturn(Optional.of(contract));
        when(hunterRepository.findById(hunter.getId())).thenReturn(Optional.of(hunter));
        when(contractRepository.save(any())).thenAnswer(args -> args.getArgument(0));
        when(hunterRepository.save(any())).thenAnswer(args -> args.getArgument(0));
        ContractEntity saved = contractService.completeContract(contract.getId(), hunter.getId());

        assertEquals(ContractStatus.COMPLETED, saved.getStatus());
        assertEquals(1, saved.getHunter().getContracts().size());
        assertEquals(110, saved.getHunter().getGold());
    }

    @Test
    void givenHunterWith2CompletedContractsAndAcceptedContract_whenCompleteContract_thenContractSavedAsCompletedAndHunterGoldAndLevelIncrease() {
        HunterEntity hunter = HunterEntity.createNew("James");
        CONTRACTS.forEach(hunter::addContract);

        ContractEntity contract = ContractEntity.createNew("mocked", "mocker", 1, 10, ContractStatus.AVAILABLE);
        contract.accept(hunter);

        when(contractRepository.findById(contract.getId())).thenReturn(Optional.of(contract));
        when(hunterRepository.findById(hunter.getId())).thenReturn(Optional.of(hunter));
        when(contractRepository.save(any())).thenAnswer(args -> args.getArgument(0));
        when(hunterRepository.save(any())).thenAnswer(args -> args.getArgument(0));
        ContractEntity saved = contractService.completeContract(contract.getId(), hunter.getId());

        assertEquals(ContractStatus.COMPLETED, saved.getStatus());
        assertEquals(3, saved.getHunter().getContracts().size());
        assertEquals(110, saved.getHunter().getGold());
        assertEquals(2, saved.getHunter().getLevel());
    }

    @Test
    void givenHunterAndCompletedContract_whenCompleteContract_thenThrowConflictException() {
        HunterEntity hunter = HunterEntity.createNew("James");

        ContractEntity contract = ContractEntity.createNew("mocked", "mocker", 1, 10, ContractStatus.AVAILABLE);
        contract.accept(hunter);
        contract.complete(hunter);

        when(contractRepository.findById(contract.getId())).thenReturn(Optional.of(contract));
        when(hunterRepository.findById(hunter.getId())).thenReturn(Optional.of(hunter));

        assertThrows(ConflictException.class,
                () -> contractService.completeContract(contract.getId(), hunter.getId()));
    }

    @Test
    void givenTwoHunterAndAcceptedContractByOneOfThem_whenCompleteContract_thenThrowForbiddenException() {
        HunterEntity hunter = HunterEntity.createNew("James");
        HunterEntity otherHunter = HunterEntity.createNew("John");

        ContractEntity contract = ContractEntity.createNew("mocked", "mocker", 1, 10, ContractStatus.AVAILABLE);
        contract.accept(hunter);

        when(contractRepository.findById(contract.getId())).thenReturn(Optional.of(contract));
        when(hunterRepository.findById(otherHunter.getId())).thenReturn(Optional.of(otherHunter));

        assertThrows(ForbiddenException.class,
                () -> contractService.completeContract(contract.getId(), otherHunter.getId()));
    }

}
