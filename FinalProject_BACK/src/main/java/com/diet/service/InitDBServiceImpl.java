package com.diet.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.diet.model.dao.FoodDao;
import com.diet.model.dto.FoodAPI;
import com.diet.model.dto.FoodFile;

@Service
public class InitDBServiceImpl implements InitDBService {

	@Value("${food.api.key}")
	private String foodapikey;

	@Autowired
	private FoodDao foodDao;
	
	// 1. foodAPI로부터 Data 받아오는 method
	// 작업 완료 후에 다음 작업 시행하도록 @Async 어노테이션 사용
	@Override
	@Async
	public void apiToDB(int st, int ed) {

		StringBuilder response = new StringBuilder();

		try {
			// api로 요청 보내기
			URL url = new URL(
					"http://openapi.foodsafetykorea.go.kr/api/" + foodapikey + "/I2790/json/" + st + "/" + ed);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
			String line;
			while ((line = br.readLine()) != null) {
				response.append(line);
			}

			System.out.println("Response: " + response.toString());

			// JSON Parsing하기
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(response.toString());

			// 데이터 Array명 "I2790"
			JSONObject I2790 = (JSONObject) jsonObject.get("I2790");
			JSONArray infoArr = (JSONArray) I2790.get("row");

			// Data DB에 입력하기
			for (int i = 0; i < infoArr.size(); i++) {

				JSONObject tmp = (JSONObject) infoArr.get(i);
				
				String NUM = ((String) tmp.get("NUM")).trim();
				String DESC_KOR = ((String) tmp.get("DESC_KOR")).trim();
				String SERVING_SIZE = ((String) tmp.get("SERVING_SIZE")).trim();
				String NUTR_CONT1 = ((String) tmp.get("NUTR_CONT1")).trim();
				String NUTR_CONT2 = ((String) tmp.get("NUTR_CONT2")).trim();
				String NUTR_CONT3 = ((String) tmp.get("NUTR_CONT3")).trim();
				String NUTR_CONT4 = ((String) tmp.get("NUTR_CONT4")).trim();

				FoodAPI food = new FoodAPI(NUM, DESC_KOR, SERVING_SIZE, NUTR_CONT1, NUTR_CONT2, NUTR_CONT3, NUTR_CONT4);
				foodDao.insertFood(food);
			}
		} catch (Exception e) {
			System.err.println("An exception occurred:");
			e.printStackTrace(System.err);
		}
	}

	// 2. JSON 파일에서 DB로 데이터 저장하기
	@Override
	@Async
	public void jsonToDB() {
		JSONParser parser = new JSONParser();

		try {
			Reader reader = new FileReader("FoodData.json");
			JSONArray datalist = (JSONArray) parser.parse(reader);
			
			for(int i=0; i<datalist.size(); i++) {
				JSONObject obj = (JSONObject) datalist.get(i);
				
				Long num = ((Long) obj.get("NUM"));
				String foodName = ((String) obj.get("foodName"));
				Double servingSize = Double.parseDouble(obj.get("servingSize").toString());
				Double kcal = Double.parseDouble(obj.get("kcal").toString());
				Double carbo = Double.parseDouble(obj.get("carbo").toString());
				Double protein = Double.parseDouble(obj.get("protein").toString());
				Double fat = Double.parseDouble(obj.get("fat").toString());

				FoodFile foodFile = new FoodFile(num, foodName, servingSize, kcal, carbo, protein, fat);
				foodDao.insertFoodFile(foodFile);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
