package com.theofaedo.huntersguild.util;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.theofaedo.huntersguild.entity.ContractEntity;
import com.theofaedo.huntersguild.entity.ContractStatus;
import com.theofaedo.huntersguild.repository.ContractRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LocalGenerator {

    private final ContractRepository contractRepository;

    private static final Set<ContractEntity> contracts = Set.of(
            ContractEntity.createNew("Rat Problem", "Giant Rat", 1, 25, ContractStatus.AVAILABLE),
            ContractEntity.createNew("Troll Bridge", "Troll", 3, 100, ContractStatus.AVAILABLE),
            ContractEntity.createNew("Dragon Hunt", "Dragon", 10, 1000, ContractStatus.AVAILABLE));

    @PostConstruct
    protected void populate() {
        contractRepository.saveAll(contracts);
    }
}
