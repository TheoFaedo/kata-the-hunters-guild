package com.theofaedo.huntersguild.service;

import org.springframework.stereotype.Service;

import com.theofaedo.huntersguild.entity.HunterEntity;
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

}
