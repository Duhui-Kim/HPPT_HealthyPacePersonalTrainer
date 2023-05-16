package com.diet.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diet.model.dao.FoodDao;
import com.diet.model.dto.FoodAPI;
import com.diet.model.dto.FoodFile;
import com.diet.service.InitDBService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/init")
public class InitDBController {

	@Autowired
	private InitDBService foodService;
	
	@GetMapping
	@ApiOperation(value = "DB에 FoodAPI 데이터를 삽입하는 method입니다.", notes = "최초 1회 실행 필요.")
	public void init() {

		// 1. JSON 파일이 없을 경우 => foodAPI로부터 받아와 DB에 저장
		// 한 번에 최대 1000개씩만 요청 가능..
//		for(int i=1; i<90608; i += 1000) {
//			foodService.apiToDB(i, i+999);
//		}

		// 2. JSON 파일이 있을 경우 => JSON 파일 파싱해서 DB에 저장
		foodService.jsonToDB();
	}
}
