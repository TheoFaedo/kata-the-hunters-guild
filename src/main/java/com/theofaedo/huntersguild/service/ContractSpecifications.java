package com.theofaedo.huntersguild.service;

import org.springframework.data.jpa.domain.Specification;

import com.theofaedo.huntersguild.entity.ContractEntity;
import com.theofaedo.huntersguild.entity.ContractStatus;

public final class ContractSpecifications {

    public static Specification<ContractEntity> hasStatus(ContractStatus status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<ContractEntity> minReward(Integer min) {
        return (root, query, cb) -> min == null ? null : cb.greaterThanOrEqualTo(root.get("reward"), min);
    }

}
