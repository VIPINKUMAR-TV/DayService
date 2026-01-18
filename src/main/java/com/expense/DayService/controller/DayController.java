package com.expense.DayService.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expense.DayService.DTO.DayDTO;
import com.expense.DayService.model.Day;
import com.expense.DayService.repository.DayRepository;
import com.expense.DayService.service.DayService;

@RestController
@RequestMapping("/api/days")
public class DayController {
	
	private final DayService service;

	public DayController(DayService service) {
        this.service = service;
    }
	 
    
    @GetMapping
    public List<DayDTO> getAllDays() {System.out.println("\n\nday controller calling dayservice");
        return service.getAllDaysWithCategories();
    //return service.getAllDaysForCurrentUser();
    }
    
    @PostMapping("/{dayId}/categories/{catId}")
    public ResponseEntity<?> addCategoryToDay(@PathVariable Long dayId, @PathVariable Long catId) {
    	DayDTO updatedDay =service.addCategoryToDay(dayId, catId);
        //Map<String, Object> body = Map.of("message", "Category mapped", "dayId", dayId, "catId", catId);
        return ResponseEntity.ok(updatedDay);
    }
    
    @PostMapping("/{dayId}/categories/{catId}/amounts")
    public DayDTO addAmount(@PathVariable Long dayId,
                         @PathVariable Long catId,
                         @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        return service.addAmountToCategory(dayId, catId, amount);
    }
    
 // ------------------ CRUD -------------------

    @GetMapping("/all")
    public List<DayDTO> getAll() {
        return service.getAllDays();
    }

    @PostMapping
    public DayDTO create(@RequestBody DayDTO dayDto) {
    	System.out.println("day controller-create()-day="+dayDto);
        return service.saveDay(dayDto);
    }

    @GetMapping("/{id}")
    public DayDTO getById(@PathVariable Long id) {
        return service.getDayById(id);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteDay(id);
    }
    @DeleteMapping("/{dayId}")
    public ResponseEntity<?> softDeleteDay(@PathVariable Long dayId) {
        service.softDeleteDay(dayId);
        return ResponseEntity.ok(Map.of("message", "Day deleted", "dayId", dayId));
    }
    @PostMapping("/{dayId}/undo-delete")
    public ResponseEntity<DayDTO> undoDeleteDay(@PathVariable Long dayId) {
    	DayDTO recoveredDay=service.undoDeleteDay(dayId);
        return ResponseEntity.ok(recoveredDay);
       // return ResponseEntity.ok(Map.of("message", "Day restored", "dayId", dayId));
    }
    

}
