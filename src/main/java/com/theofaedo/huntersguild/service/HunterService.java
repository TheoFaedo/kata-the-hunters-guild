package com.theofaedo.huntersguild.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.theofaedo.huntersguild.dto.HunterStatsDto;
import com.theofaedo.huntersguild.entity.ContractEntity;
import com.theofaedo.huntersguild.entity.HunterEntity;
import com.theofaedo.huntersguild.exception.NotFoundException;
import com.theofaedo.huntersguild.repository.HunterRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HunterService {

    private final HunterRepository repository;

    public HunterEntity createHunter(String name) {
        return repository.save(
                HunterEntity.createNew(name));
    }

    public HunterStatsDto findStats(UUID hunterId) {
        HunterEntity hunter = repository.findById(hunterId)
                .orElseThrow(() -> new NotFoundException("Hunter not found"));

        final List<ContractEntity> completedContracts = hunter.getContracts().stream()
                .filter(c -> c.isCompleted()).toList();

        final int totalGoldEarned = completedContracts.stream()
                .mapToInt(ContractEntity::getReward)
                .sum();

        return new HunterStatsDto(hunter.getId(), hunter.getName(), hunter.getLevel(), hunter.getGold(),
                completedContracts.size(), totalGoldEarned);
    }

}
