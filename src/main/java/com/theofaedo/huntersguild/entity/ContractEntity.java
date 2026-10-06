package com.theofaedo.huntersguild.entity;

import java.util.UUID;

import com.theofaedo.huntersguild.exception.ConflictException;
import com.theofaedo.huntersguild.exception.ForbiddenException;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CONTRACTS")
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

    @Version
    private long version;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hunter_id", nullable = true)
    private HunterEntity hunter;

    public static ContractEntity createNew(String title,
            String monster,
            int level,
            int reward, ContractStatus status) {
        return new ContractEntity(UUID.randomUUID(), title, monster, level, reward, status, 0L, null);
    }

    public void accept(HunterEntity hunter) {
        if (this.status != ContractStatus.AVAILABLE) {
            throw new ConflictException("Contract is already accepted");
        }

        if (hunter.getLevel() < this.level) {
            throw new ForbiddenException("Hunter level is too low for this contract");
        }

        this.status = ContractStatus.ACCEPTED;

        hunter.addContract(this);
        this.hunter = hunter;
    }

    public void complete(HunterEntity hunter) {
        if (!this.isAccepted()) {
            throw new ConflictException("Only accepted contracts can be completed");
        }

        if (!this.hunter.getId().equals(hunter.getId())) {
            throw new ForbiddenException("This contract is owned by another hunter");
        }

        if (this.isCompleted()) {
            throw new ConflictException("This contract is already completed");
        }

        this.status = ContractStatus.COMPLETED;
        this.hunter.acceptReward(reward);
    }

    private boolean isAccepted() {
        return this.status == ContractStatus.ACCEPTED;
    }

    public boolean isCompleted() {
        return this.status == ContractStatus.COMPLETED;
    }
}
