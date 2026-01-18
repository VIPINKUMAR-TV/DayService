package com.expense.DayService.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.expense.DayService.DTO.CategoryInfo;
import com.expense.DayService.DTO.DayDTO;
import com.expense.DayService.client.CategoryClient;
import com.expense.DayService.mapper.DayMapper;
import com.expense.DayService.model.Day;
import com.expense.DayService.model.DayCategory;
import com.expense.DayService.repository.DayRepository;
import com.expense.DayService.util.RequestHeaderUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class DayServiceImp implements DayService{
	 private final DayRepository dayRepository;
	 private final CategoryClient categoryClient;
	 private final HttpServletRequest request;
	 private final DayMapper dayMapper;  
	
	 
	 @Value("${category.service.url}")
	 private String categoryServiceUrl; // e.g. http://localhost:8082/api/categories/

		// Fetch all days
		public List<DayDTO> getAllDays() {
			List<Day> allDaysRetrieved= dayRepository.findAll();
			return dayMapper.toDtoList(allDaysRetrieved);
		}

		public DayDTO getDayById(Long dayId) {

			//Long userId = extractUserId();
			Long userId = RequestHeaderUtil.extractUserId(request);
			Day day = dayRepository.findByDayIdAndUserId(dayId, userId)
					.orElseThrow(() -> new RuntimeException("Day not found: " + dayId));
			return dayMapper.toDto(day);
		}

		public List<DayDTO> getAllDaysForCurrentUser() {

			//Long userId = extractUserId();
			Long userId = RequestHeaderUtil.extractUserId(request);
			List<Day> days = dayRepository.findByUserIdOrderByDateDesc(userId);
			enrichCategories(days); // still enrich transient fields
			return dayMapper.toDtoList(days);
		}
		
		 public List<DayDTO> getAllDaysWithCategories() {
		    	
		    	Long userId = extractUserId();
		        //List<Day> days = dayRepository.findByUserIdOrderByDateDesc(userId);
		        List<Day> days = dayRepository.findByUserIdAndIsDeletedFalseOrderByDateDesc(userId);

		        for (Day day : days) {
		            day.getCategories().forEach(cat -> {
		                try {
		                    CategoryInfo info = categoryClient.getCategoryById(cat.getCatId());
		                    if (info != null) {
		                        cat.setCatName(info.getCatName());
		                        cat.setCatStatus(info.getCatStatus());
		                    }
		                } catch (Exception e) {
		                    System.out.println("Failed category fetch: " + e.getMessage());
		                }
		            });
		        }
		        //return days;
		        return dayMapper.toDtoList(days);
		    }

		// Save a new day
		public DayDTO saveDay(DayDTO dayDto) {
			Day day = dayMapper.toEntity(dayDto);
			Long userId = extractUserId();
			day.setUserId(userId);

			// If categories are not provided, initialize from CategoryService
			if (day.getCategories() == null || day.getCategories().isEmpty()) {

				List<CategoryInfo> categories = categoryClient.getAllCategories();

				List<DayCategory> dayCategories = categories.stream().map(c -> {
					DayCategory dc = new DayCategory();
	                        dc.setCatId(c.getCatId());
	                        dc.setCatName(c.getCatName());
	                        dc.setCatStatus(c.getCatStatus());
	                        dc.setAmounts(new ArrayList<>());
	                        dc.setDay(day);
	                        return dc;
	                    })
	                    .toList();

	            day.setCategories(dayCategories);
	        }

			Day saved = dayRepository.save(day);
			//enrichCategoryForSingleDay(saved);

		    return dayMapper.toDto(saved);
	    }
	    //category mapping day wise
	    public DayDTO addCategoryToDay(Long dayId, Long catId) {

	    	Long userId = extractUserId();

	        Day day = dayRepository.findByDayIdAndUserId(dayId, userId)
	                .orElseThrow(() -> new RuntimeException("Day not found"));
	        
	        CategoryInfo category = categoryClient.getCategoryById(catId);

	        DayCategory dc = new DayCategory();
	        dc.setCatId(catId);
	        //dc.setCatName(category.getCatName());
	        //dc.setCatStatus(category.getCatStatus());
	        dc.setDay(day);

	        day.getCategories().add(dc);
	        Day saved =dayRepository.save(day);
	     // 2️⃣ Now assign transient fields
	        saved.getCategories().stream()
	                .filter(c -> c.getCatId().equals(catId))
	                .forEach(c -> {
	                    c.setCatName(category.getCatName());
	                    c.setCatStatus(category.getCatStatus());
	                });

	        // 3️⃣ Return enriched entity
	        return dayMapper.toDto(saved);
	    }
	    
	    @Transactional
	    public DayDTO addAmountToCategory(Long dayId, Long catId, Double amount) {

	    	Long userId = extractUserId();

	        Day day = dayRepository.findByDayIdAndUserId(dayId, userId)
	                .orElseThrow(() -> new RuntimeException("Day not found"));

	        DayCategory category = day.getCategories().stream()
	                .filter(c -> c.getCatId().equals(catId))
	                .findFirst()
	                .orElseThrow(() -> new RuntimeException("Category not found"));

	        category.getAmounts().add(amount);

	        Day saved= dayRepository.save(day);
	        return dayMapper.toDto(saved);
	    }

	    public void deleteDay(Long dayId) {
	    	Long userId = extractUserId();
	        dayRepository.deleteByDayIdAndUserId(dayId, userId);
	    }
	    
	    public void softDeleteDay(Long dayId) {
	        Long userId = extractUserId();

	        Day day = dayRepository.findByDayIdAndUserId(dayId, userId)
	                .orElseThrow(() -> new RuntimeException("Day not found"));

	        day.setDeleted(true);
	        dayRepository.save(day);
	    }
	    public DayDTO undoDeleteDay(Long dayId) {
	        Long userId = extractUserId();

	        Day day = dayRepository.findByDayIdAndUserId(dayId, userId)
	                .orElseThrow(() -> new RuntimeException("Day not found"));

	        day.setDeleted(false);
	        Day retrieved= dayRepository.save(day);
	        return dayMapper.toDto(retrieved);
	    }
	    private Long extractUserId() {
	        String id = request.getHeader("X-User-Id");
	        if (id == null || id.isBlank()) {
	            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing X-User-Id header (Gateway did not forward auth)");
	        }
	        try {
	            return Long.parseLong(id);
	        } catch (NumberFormatException ex) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid X-User-Id header");
	        }
	    }
	    
	    private void enrichCategories(List<Day> days) {
	        days.forEach(this::enrichCategoryForSingleDay);
	    }

	    private void enrichCategoryForSingleDay(Day day) {
	        for (DayCategory cat : day.getCategories()) {
	            try {
	                CategoryInfo info = categoryClient.getCategoryById(cat.getCatId());
	                if (info != null) {
	                    cat.setCatName(info.getCatName());
	                    cat.setCatStatus(info.getCatStatus());
	                }
	            } catch (Exception ignored) {}
	        }
	    }
	    

	    
	    
	    
}
