package com.theofaedo.huntersguild.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.theofaedo.huntersguild.dto.ContractDto;
import com.theofaedo.huntersguild.entity.ContractEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ContractMapper {

    ContractDto toDto(ContractEntity contract);

}
