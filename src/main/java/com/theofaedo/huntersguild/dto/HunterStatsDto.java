package com.theofaedo.huntersguild.dto;

import java.util.UUID;

public record HunterStatsDto(UUID id,
        String name,
        int level,
        int gold, int completedContracts, int totalGoldEarned) {

}
