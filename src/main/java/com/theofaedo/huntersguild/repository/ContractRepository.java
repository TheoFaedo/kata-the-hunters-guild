package com.theofaedo.huntersguild.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.theofaedo.huntersguild.entity.ContractEntity;

public interface ContractRepository
        extends JpaRepository<ContractEntity, UUID>, JpaSpecificationExecutor<ContractEntity> {

}
