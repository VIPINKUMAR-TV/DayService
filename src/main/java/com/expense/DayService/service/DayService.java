package com.expense.DayService.service;

import java.util.List;

import com.expense.DayService.DTO.DayDTO;
import com.expense.DayService.model.Day;

public interface DayService {
	
	public List<DayDTO> getAllDays();
	public DayDTO getDayById(Long dayId);
	public List<DayDTO> getAllDaysForCurrentUser();
	public List<DayDTO> getAllDaysWithCategories();
	
	public DayDTO saveDay(DayDTO dayDto);
	public DayDTO addCategoryToDay(Long dayId, Long catId);
	public DayDTO addAmountToCategory(Long dayId, Long catId, Double amount);
	
	public void deleteDay(Long dayId);
	public void softDeleteDay(Long dayId);
	public DayDTO undoDeleteDay(Long dayId);
	

}
