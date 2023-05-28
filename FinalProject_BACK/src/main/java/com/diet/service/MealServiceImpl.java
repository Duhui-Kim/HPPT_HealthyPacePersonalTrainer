package com.diet.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.diet.model.dao.MealDao;
import com.diet.model.dao.UserDao;
import com.diet.model.dto.Meal;
import com.diet.model.dto.User;

@Service
public class MealServiceImpl implements MealService {

	private MealDao mealDao;
	@Autowired
	private UserDao userDao;

	@Autowired
	private FileService fileService;

	@Autowired
	public void setMealDao(MealDao mealDao) {
		this.mealDao = mealDao;
	}

	@Override
	// 음식 이름 검색
	public List<Meal> searchFoodByName(String menuName) {
		List<Meal> list = mealDao.selectFoodByName(menuName);
		
		for (Meal m : list) {
			double intake = m.getIntake();
			if (intake == 100)
				continue;
			else {
				m.setMenuKcal(Math.round(m.getMenuKcal() / m.getIntake() * 10000) / 100.0);
				m.setCarbo(Math.round(m.getCarbo() / m.getIntake() * 10000) / 100.0);
				m.setProtein(Math.round(m.getProtein() / m.getIntake() * 10000) / 100.0);
				m.setFat(Math.round(m.getFat() / m.getIntake() * 10000) / 100.0);
				m.setIntake(100);
			}
		}

		return list;
	}

	@Override
	// 식사 등록
	public void registerMeal(Meal meal) {
		double intake = meal.getIntake();
		meal.setMenuKcal(meal.getMenuKcal() / 100 * intake);
		meal.setCarbo(meal.getCarbo() / 100 * intake);
		meal.setProtein(meal.getProtein() / 100 * intake);
		meal.setFat(meal.getFat() / 100 * intake);

		mealDao.insertMeal(meal);

	}

	@Override
	// 식사 삭제
	public void removeMeal(int mealId) {
		mealDao.deleteMeal(mealId);
	}

	@Override
	// 날짜별 등록한 식단 정보 가져오기
	public Map<String, List<Meal>> searchMealByDate(String userId, String date) {
		// 전체 아침 점심 저녁 나눠서 보내주기
		Map<String, List<Meal>> map = new HashMap<>();

		List<Meal> list = new ArrayList<>();
		List<Meal> morning = new ArrayList<>();
		List<Meal> lunch = new ArrayList<>();
		List<Meal> dinner = new ArrayList<>();
		
		for (Meal m : mealDao.selectMealByDate(userId, date)) {
			m.setMenuKcal(Math.round(m.getMenuKcal() * 100) / 100.0);
			m.setCarbo(Math.round(m.getCarbo() * 100) / 100.0);
			m.setProtein(Math.round(m.getProtein() * 100) / 100.0);
			m.setFat(Math.round(m.getFat() * 100) / 100.0);
			list.add(m);
			
			switch(m.getDivideTime()) {
			case 1:
				morning.add(m);
				break;
			case 2:
				lunch.add(m);
				break;
			case 3:
				dinner.add(m);
			}
		}
		map.put("total", list);
		map.put("morning", morning);
		map.put("lunch", lunch);
		map.put("dinner", dinner);
		
		return map;
	}

	@Override
	public boolean registImg(int mealId, MultipartFile multipartFile) {
		Meal meal = mealDao.selectMealById(mealId);

		if (meal == null)
			return false;

		try {
			String fileName = fileService.upload(multipartFile);

			meal.setMealImg(fileName);
			mealDao.updateImg(meal);

			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String getFileName(int mealId) {
		return mealDao.selectFileName(mealId);
	}

	@Override
	public double getRemainKcal(String userId) {
		
		List<Meal> meals = mealDao.selectMealByDate(userId, "null");
        Comparator<Meal> dateComparator = Comparator.comparing(Meal::getEatingTime);
        
		Collections.sort(meals, dateComparator);
		
		double cnt = 0;
		double kcal = 0; 
		String prev = "";
		
		for(Meal meal : meals) {
			kcal += meal.getMenuKcal();
			if(!prev.equals(meal.getEatingTime())) {
				cnt++;
				prev = meal.getEatingTime();
			}
		}
		
		User user = userDao.selectById(userId);
		
		double remainKcal;
		
		if(user.getUserSex() == 1) {
			remainKcal = user.getStartKcal() - ((73 * 0.9 * 32.5 - 500) * cnt - kcal);
		} else {
			remainKcal = user.getStartKcal() - ((60 * 0.9 * 32.5 - 500) * cnt - kcal);			
		}
		user.setRemainKcal(remainKcal);
		
		userDao.update(user);
		
		return remainKcal;
	}
}
