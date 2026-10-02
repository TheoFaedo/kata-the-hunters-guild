package com.theofaedo.huntersguild.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateHunterDto(
                @NotBlank @Size(min = 1, max = 30, message = "Name length should be between 1 and 30 characters") String name) {
}
