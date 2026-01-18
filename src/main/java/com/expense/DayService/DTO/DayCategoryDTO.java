package com.expense.DayService.DTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DayCategoryDTO {
	private Long id;
    private Long catId;

    private String catName;     // Filled from CategoryService
    private String catStatus;   // Filled from CategoryService

    private List<Double> amounts;

}
