package com.theofaedo.huntersguild.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theofaedo.huntersguild.dto.CreateHunterDto;
import com.theofaedo.huntersguild.dto.HunterDto;
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

}
