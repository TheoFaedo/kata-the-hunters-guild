package com.theofaedo.huntersguild.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.theofaedo.huntersguild.dto.HunterDto;
import com.theofaedo.huntersguild.entity.HunterEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HunterMapper {

    HunterDto toDto(HunterEntity hunter);

}
