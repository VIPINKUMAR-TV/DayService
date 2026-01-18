package com.expense.DayService.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.expense.DayService.DTO.CategoryInfo;

//URL points to CategoryService base
@FeignClient(name = "CATEGORY-SERVICE", url = "${category.service.urlfe}")
public interface CategoryClient {
	
	// Equivalent to GET /api/categories
    @GetMapping("/api/categories")
    List<CategoryInfo> getAllCategories();

	@GetMapping("/api/categories/{id}")
    CategoryInfo getCategoryById(@PathVariable("id") Long id);
}
