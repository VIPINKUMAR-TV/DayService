package com.expense.DayService.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "days",schema = "daydb")
@Getter
@Setter
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dayId;

    private Long userId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    
    @Column(nullable = false)
    private boolean isDeleted = false;

    /** 
     * LAZY = professional standard (avoids N+1)
     * mappedBy = clean bidirectional mapping
     */
    @OneToMany(
    	    mappedBy = "day",
    	    cascade = CascadeType.ALL,
    	    orphanRemoval = true,
    	    fetch = FetchType.LAZY
    	)
    	@JsonManagedReference
    	private List<DayCategory> categories = new ArrayList<>();

    // Constructors
    public Day() {}

    public Day(LocalDate date, Long userId) {
        this.date = date;
        this.userId = userId;
    }

    // getters & setters
}
