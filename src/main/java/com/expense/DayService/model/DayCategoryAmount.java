//package com.expense.DayService.model;
//
//import java.util.List;
//
//import jakarta.persistence.ElementCollection;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//
////@Entity
////@Table(name = "day_category_amounts")
//public class DayCategoryAmount {
//	 @Id
//	    @GeneratedValue(strategy = GenerationType.IDENTITY)
//	    private Long id;
//
//	    private Long catId; // references Category (from category microservice)
//
//	    @ElementCollection
//	    private List<Double> amounts;
//
//	    @ManyToOne
//	    @JoinColumn(name = "day_id")
//	    private Day day;
//
//		public Long getId() {
//			return id;
//		}
//
//		public void setId(Long id) {
//			this.id = id;
//		}
//
//		public Long getCatId() {
//			return catId;
//		}
//
//		public void setCatId(Long catId) {
//			this.catId = catId;
//		}
//
//		public List<Double> getAmounts() {
//			return amounts;
//		}
//
//		public void setAmounts(List<Double> amounts) {
//			this.amounts = amounts;
//		}
//
//		public Day getDay() {
//			return day;
//		}
//
//		public void setDay(Day day) {
//			this.day = day;
//		}
//
//	    // getters & setters
//	    
//	    
//}
