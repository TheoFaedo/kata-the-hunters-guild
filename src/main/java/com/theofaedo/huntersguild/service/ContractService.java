package com.theofaedo.huntersguild.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.theofaedo.huntersguild.entity.ContractEntity;
import com.theofaedo.huntersguild.entity.ContractsQuery;
import com.theofaedo.huntersguild.repository.ContractRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository repository;

    public List<ContractEntity> all(ContractsQuery query) {

        Specification<ContractEntity> spec = Specification
                .where(ContractSpecifications.hasStatus(query.status()))
                .and(ContractSpecifications.minReward(query.minReward()));

        return repository.findAll(spec);
    }

}
