package com.theofaedo.huntersguild.dto;

import com.theofaedo.huntersguild.entity.ContractStatus;

public record ContractDto(String title, String monster, int level, int reward, ContractStatus status) {

}
