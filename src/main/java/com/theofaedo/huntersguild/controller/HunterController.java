package com.theofaedo.huntersguild.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theofaedo.huntersguild.dto.CreateHunterDto;
import com.theofaedo.huntersguild.dto.HunterDto;
import com.theofaedo.huntersguild.dto.HunterStatsDto;
import com.theofaedo.huntersguild.entity.HunterEntity;
import com.theofaedo.huntersguild.mapper.HunterMapper;
import com.theofaedo.huntersguild.service.HunterService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/hunters")
@RequiredArgsConstructor
public class HunterController {

    private final HunterService hunterService;
    private final HunterMapper hunterMapper;

    @PostMapping
    public HunterDto createHunter(@Valid @RequestBody CreateHunterDto dto) {
        final HunterEntity hunter = hunterService.createHunter(dto.name());

        return hunterMapper.toDto(hunter);
    }

    @GetMapping("/{id}/stats")
    public HunterStatsDto stats(@PathVariable UUID id) {
        return hunterService.findStats(id);
    }

}
