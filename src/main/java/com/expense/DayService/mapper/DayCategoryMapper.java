package com.expense.DayService.mapper;

import org.mapstruct.Mapper;

import com.expense.DayService.DTO.DayCategoryDTO;
import com.expense.DayService.model.DayCategory;

@Mapper(componentModel = "spring")
public interface DayCategoryMapper {
	DayCategoryDTO toDto(DayCategory entity);

    DayCategory toEntity(DayCategoryDTO dto);
}
