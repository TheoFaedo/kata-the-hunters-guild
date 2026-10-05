package com.theofaedo.huntersguild.entity;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "HUNTERS")
@Getter
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class HunterEntity {

    @Id
    @Column(name = "hunder_id")
    private UUID id;

    private String name;
    private int gold;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hunter", fetch = FetchType.EAGER)
    private List<ContractEntity> contracts = new ArrayList<>();

    public static HunterEntity createNew(String name) {
        return new HunterEntity(UUID.randomUUID(), name, 100, new ArrayList<>());
    }

    public void addContract(ContractEntity contractEntity) {
        this.contracts.add(contractEntity);
    }

    public void acceptReward(int gold) {
        this.gold += gold;
    }

    @Transient
    public int getLevel() {
        return contracts.stream().filter(c -> c.isCompleted()).toList().size() / 3 + 1;
    }
}
