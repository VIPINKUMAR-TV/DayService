package com.expense.DayService.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "day_categories")
@Getter
@Setter
public class DayCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long catId;   // Only ID stored (name/status from CategoryService)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "day_id")
    @JsonBackReference
    private Day day;

    // Filled from CategoryService → not stored in DB
    @Transient
    private String catName;

    @Transient
    private String catStatus;

    /**
     * ElementCollection = correct & optimized way to store amounts
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "day_category_amounts",
        joinColumns = @JoinColumn(name = "day_category_id")
    )
    @Column(name = "amount")
    private List<Double> amounts = new ArrayList<>();

    // getters & setters
}