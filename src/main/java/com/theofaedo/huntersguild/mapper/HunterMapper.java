package com.theofaedo.huntersguild.mapper;

import org.mapstruct.Mapper;

import com.theofaedo.huntersguild.dto.HunterDto;
import com.theofaedo.huntersguild.entity.HunterEntity;

@Mapper(componentModel = "spring")
public interface HunterMapper {

    HunterDto toDto(HunterEntity hunter);

}
