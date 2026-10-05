package com.theofaedo.huntersguild.dto;

import java.util.UUID;

import com.theofaedo.huntersguild.entity.ContractStatus;

public record ContractDto(UUID id, String title, String monster, int level, int reward, ContractStatus status) {

}
