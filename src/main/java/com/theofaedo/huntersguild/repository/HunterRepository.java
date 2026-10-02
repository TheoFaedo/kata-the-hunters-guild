package com.theofaedo.huntersguild.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.theofaedo.huntersguild.entity.HunterEntity;

public interface HunterRepository extends JpaRepository<HunterEntity, UUID> {

}
