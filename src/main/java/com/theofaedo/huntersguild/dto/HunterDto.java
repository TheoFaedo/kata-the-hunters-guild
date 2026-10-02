package com.theofaedo.huntersguild.dto;

import java.util.UUID;

public record HunterDto(
        UUID id,
        String name,
        int level,
        int gold) {
}