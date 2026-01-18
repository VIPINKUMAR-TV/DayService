package com.expense.DayService.DTO;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class DayDTO {
	 	private Long dayId;
	    private Long userId;
	    private LocalDate date;
	    private boolean isDeleted;

	    private List<DayCategoryDTO> categories;
//	 private Long dayId;
//	    private List<CategoryAmountDTO> categories;
//	    private LocalDate date;
//
//	    public static class CategoryAmountDTO {
//	        private Long catId;
//	        private List<Double> amounts;
//
//	        // Getters & Setters
//	        public Long getCatId() { return catId; }
//	        public void setCatId(Long catId) { this.catId = catId; }
//
//	        public List<Double> getAmounts() { return amounts; }
//	        public void setAmounts(List<Double> amounts) { this.amounts = amounts; }
//	    }
//
//	    // Getters & Setters
//	    public Long getDayId() { return dayId; }
//	    public void setDayId(Long dayId) { this.dayId = dayId; }
//
//	    public List<CategoryAmountDTO> getCategories() { return categories; }
//	    public void setCategories(List<CategoryAmountDTO> categories) { this.categories = categories; }

}
