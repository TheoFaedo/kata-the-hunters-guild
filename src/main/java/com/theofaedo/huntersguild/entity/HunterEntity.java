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
@Table(name = "HUNTER")
@Getter
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class HunterEntity {

    @Id
    private UUID id;

    private String name;
    private int level;
    private int gold;

    public static HunterEntity createNew(String name) {
        return new HunterEntity(UUID.randomUUID(), name, 1, 100);
    }
}
