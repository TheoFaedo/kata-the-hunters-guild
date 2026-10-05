package com.theofaedo.huntersguild.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.theofaedo.huntersguild.entity.ContractEntity;
import com.theofaedo.huntersguild.entity.ContractsQuery;
import com.theofaedo.huntersguild.entity.HunterEntity;
import com.theofaedo.huntersguild.exception.NotFoundException;
import com.theofaedo.huntersguild.repository.ContractRepository;
import com.theofaedo.huntersguild.repository.HunterRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository repository;
    private final HunterRepository hunterRepository;

    public List<ContractEntity> all(ContractsQuery query) {

        Specification<ContractEntity> spec = Specification
                .where(ContractSpecifications.hasStatus(query.status()))
                .and(ContractSpecifications.minReward(query.minReward()));

        return repository.findAll(spec);
    }

    @Transactional
    public ContractEntity acceptContract(UUID contractId, UUID hunterId) {
        ContractEntity contract = repository.findById(contractId)
                .orElseThrow(() -> new NotFoundException("Contract not found"));
        HunterEntity hunter = hunterRepository.findById(hunterId)
                .orElseThrow(() -> new NotFoundException("Hunter not found"));

        contract.accept(hunter);

        final ContractEntity updatedContract = repository.save(contract);
        hunterRepository.save(hunter);

        return updatedContract;
    }

    @Transactional
    public ContractEntity completeContract(UUID contractId, UUID hunterId) {
        ContractEntity contract = repository.findById(contractId)
                .orElseThrow(() -> new NotFoundException("Contract not found"));
        HunterEntity hunter = hunterRepository.findById(hunterId)
                .orElseThrow(() -> new NotFoundException("Hunter not found"));

        contract.complete(hunter);

        final ContractEntity updatedContract = repository.save(contract);
        hunterRepository.save(hunter);

        return updatedContract;
    }

}
