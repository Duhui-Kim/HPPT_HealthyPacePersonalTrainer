package com.diet.model.dao;

import com.diet.model.dto.FoodAPI;
import com.diet.model.dto.FoodFile;

public interface FoodDao {
	public void insertFood(FoodAPI food);
	
	public void insertFoodFile(FoodFile foodFile);
}
