package com.theofaedo.huntersguild.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record HunterIdDto(@NotBlank UUID hunterId) {

}
