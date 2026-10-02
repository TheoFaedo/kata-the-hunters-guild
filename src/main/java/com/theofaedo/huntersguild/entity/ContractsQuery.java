package com.theofaedo.huntersguild.entity;

import lombok.Builder;

@Builder
public record ContractsQuery(ContractStatus status, Integer minReward) {

}
