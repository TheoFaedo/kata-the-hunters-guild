package com.theofaedo.huntersguild.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Contract")
@Getter
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class ContractEntity {

    @Id
    private UUID id;

    private String title;
    private String monster;
    private int level;
    private int reward;
    private ContractStatus status;

    public static ContractEntity createNew(String title,
            String monster,
            int level,
            int reward, ContractStatus status) {
        return new ContractEntity(UUID.randomUUID(), title, monster, level, reward, status);
    }
}
