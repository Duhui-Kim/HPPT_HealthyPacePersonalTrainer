package com.diet.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.diet.model.dto.Meal;

public interface MealService {

	// 음식 이름 검색
	public List<Meal> searchFoodByName(String menuName);

	// 식사 등록
	public void registerMeal(Meal meal);

	// 식사 삭제
	public void removeMeal(int mealId);

	// 날짜별 등록한 식단 정보 가져오기
	public Map<String, List<Meal>> searchMealByDate(String userId, String date);

	// mealId에 해당하는 식단에 ImgFile 등록하기
	public boolean registImg(int mealId, MultipartFile multipartFile);

	// mealId에 등록된 fileName 가져오기
	public String getFileName(int mealId);

	// userId의 남은 칼로리 가져오기
	public double getRemainKcal(String userId);
}
