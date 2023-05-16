package com.diet.service;

public interface InitDBService {
	// API 데이터를 DB에 저장하는 method
	public void apiToDB(int st, int ed);
	// JSON File 데이터를 DB에 저장하는 method
	public void jsonToDB();
}
