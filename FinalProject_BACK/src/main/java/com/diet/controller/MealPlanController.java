package com.diet.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.diet.model.dto.ImgFile;
import com.diet.model.dto.Meal;
import com.diet.service.FileService;
import com.diet.service.MealService;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/mealPlan")
public class MealPlanController {

	@Autowired
	private MealService mealService;
	
	@Autowired
	private FileService fileService;

	// 1. 식사 등록
	@PostMapping
	@ApiOperation(value = "식사 등록 method", notes = "필수 : userId, menuName, menuKcal, carbo, protein, fat, intake, eatingTime, divideTime")
	public ResponseEntity<?> reigstMeal(@RequestBody Meal meal) {
		mealService.registerMeal(meal);

		return new ResponseEntity<Meal>(meal, HttpStatus.CREATED);
	}

	// 2. 식사 삭제
	@DeleteMapping("/{mealId}")
	@ApiOperation(value = "식사 삭제 method", notes = "필수 : mealId")
	public ResponseEntity<Void> deleteMeal(@PathVariable int mealId) {
		mealService.removeMeal(mealId);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	// 3. 음식 이름으로 DB에서 검색
	@GetMapping("/search/{mealName}")
	@ApiOperation(value = "{mealName}을 포함하는 음식을 FoodAPI에서 검색", notes = "필수 : mealName")
	public ResponseEntity<?> searchMenu(@PathVariable String mealName) {
		List<Meal> list = mealService.searchFoodByName(mealName);

		if (list == null || list.size() == 0)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<Meal>>(list, HttpStatus.OK);
	}

	// 4. {userId}의 유저가 {date}에 먹은 음식 가져오기
	@GetMapping("/{userId}/{date}")
	@ApiOperation(value = "{userId}의 유저가 {date}에 먹은 음식 가져오기", notes = "필수 : userId, date")
	public ResponseEntity<?> UserAteListbyDate(@PathVariable String userId, @PathVariable String date) {		
		Map<String, List<Meal>> list = mealService.searchMealByDate(userId, date);

		return new ResponseEntity<Map<String, List<Meal>>>(list, HttpStatus.OK);
	}
	
	// 5. 음식 이미지 등록
	@PostMapping("/img/{mealId}")
	@ApiOperation(value = "{mealId}에 img 등록", notes = "필수 : mealId, imgFile")
	public ResponseEntity<?> addImgFile(@PathVariable int mealId, @RequestParam MultipartFile imgFile) {
		boolean registed = mealService.registImg(mealId, imgFile);
		
		if (registed) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
	}
	// 6. 음식 이미지 가져오기
	@GetMapping("/img/{mealId}")
	@ApiOperation(value = "{mealId} img 가져오기", notes = "필수 : mealId")
	public ResponseEntity<?> addImgFile(@PathVariable int mealId) {
		String fileName = mealService.getFileName(mealId);
		
		if(fileName == null)
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		
		ImgFile imgdata = fileService.getFileData(fileName);

		if (imgdata == null)
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);

		byte[] imgFile = fileService.download(fileName);

		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(imgdata.getType())).body(imgFile);
	}
	
	// 7. 유저의 남은 칼로리 가져오기
	@GetMapping("/remain/{userId}")
	@ApiOperation(value = "{userId}의 남은 칼로리 가져오기", notes = "필수 : userId")
	public ResponseEntity<?> getRemainKcal(@PathVariable String userId) {
		double remainKcal = mealService.getRemainKcal(userId);
		
		return new ResponseEntity<Double> (remainKcal, HttpStatus.OK);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> mealPlanException(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("mealPlanError", HttpStatus.CONFLICT);
	}
}
