package com.diet.model.dao;

import com.diet.model.dto.Food;
import com.diet.model.dto.FoodFile;

public interface FoodDao {
	public void insertFood(Food food);
	
	public void insertFoodFile(FoodFile foodFile);
}
