package com.theofaedo.huntersguild.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.theofaedo.huntersguild.dto.ContractDto;
import com.theofaedo.huntersguild.entity.ContractStatus;
import com.theofaedo.huntersguild.entity.ContractsQuery;
import com.theofaedo.huntersguild.mapper.ContractMapper;
import com.theofaedo.huntersguild.service.ContractService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;
    private final ContractMapper contractMapper;

    @GetMapping
    public List<ContractDto> allContracts(
            @RequestParam(required = false) ContractStatus status, @RequestParam(required = false) Integer minReward) {

        ContractsQuery query = ContractsQuery.builder()
                .status(status)
                .minReward(minReward)
                .build();

        return contractService.all(query).stream()
                .map(contractMapper::toDto)
                .toList();
    }

}
