package com.expense.DayService.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.expense.DayService.DTO.DayDTO;
import com.expense.DayService.model.Day;

@Mapper(componentModel = "spring", uses = { DayCategoryMapper.class })
public interface DayMapper {
	DayDTO toDto(Day entity);

    Day toEntity(DayDTO dto);
    List<DayDTO> toDtoList(List<Day> entityList);
}
