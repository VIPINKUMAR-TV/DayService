package com.expense.DayService.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryInfo {
	
	//@JsonProperty("cat_id")
	private Long catId;
	
	//@JsonProperty("cat_name")
    private String catName;
	
	//@JsonProperty("cat_status")
    private String catStatus;
	
    
    
    public Long getCatId() {
		return catId;
	}
	public void setCatId(Long catId) {
		this.catId = catId;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public String getCatStatus() {
		return catStatus;
	}
	public void setCatStatus(String catStatus) {
		this.catStatus = catStatus;
	}
	@Override
	public String toString() {
		return "CategoryInfo [catId=" + catId + ", catName=" + catName + ", catStatus=" + catStatus + "]";
	}
	
	

}
