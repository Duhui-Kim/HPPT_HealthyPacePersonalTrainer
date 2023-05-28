package com.diet.model.dao;

import java.util.List;

import com.diet.model.dto.Meal;

public interface MealDao {
	//음식 이름으로 DB에서 검색
	public List<Meal> selectFoodByName(String menuName);
	
	//식사 등록
	public void insertMeal(Meal meal);
	
	//식사 삭제
	public void deleteMeal(int mealId); 
	
	//날짜별 식단 정보 가져오기
	public List<Meal> selectMealByDate(String userId, String date);

	// mealId로 meal 검색
	public Meal selectMealById(int mealId);
	
	// ImgFileName 삽입
	public void updateImg(Meal meal);

	// fileName 검색
	public String selectFileName(int mealId);
}
