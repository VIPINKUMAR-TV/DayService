package com.expense.DayService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.expense.DayService.model.Day;
import com.expense.DayService.model.DayCategory;

public interface DayRepository extends JpaRepository<Day, Long>{
	List<Day> findByUserId(Long userId);
	List<Day> findByUserIdOrderByDateDesc(Long userId);
	List<Day> findByUserIdAndIsDeletedFalseOrderByDateDesc(Long userId);
	Optional<Day> findByDayIdAndUserId(Long dayId, Long userId);
	void deleteByDayIdAndUserId(Long dayId, Long userId);
	
}