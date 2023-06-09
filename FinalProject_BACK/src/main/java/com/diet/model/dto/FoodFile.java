package com.diet.model.dto;

public class FoodFile {
	// 번호
	private long num;
	// 식품명
	private String foodName;
	// 총 내용량
	private double servingSize;
	// 열량(kcal)
	private double kcal;
	// 탄수화물
	private double carbo;
	// 단백질
	private double protein;
	// 지방
	private double fat;

	public FoodFile() {
	}

	public FoodFile(long num, String foodName, double servingSize, double kcal, double carbo, double protein,
			double fat) {
		super();
		this.num = num;
		this.foodName = foodName;
		this.servingSize = servingSize;
		this.kcal = kcal;
		this.carbo = carbo;
		this.protein = protein;
		this.fat = fat;
	}

	public long getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public double getServingSize() {
		return servingSize;
	}

	public void setServingSize(double servingSize) {
		this.servingSize = servingSize;
	}

	public double getKcal() {
		return kcal;
	}

	public void setKcal(double kcal) {
		this.kcal = kcal;
	}

	public double getCarbo() {
		return carbo;
	}

	public void setCarbo(double carbo) {
		this.carbo = carbo;
	}

	public double getProtein() {
		return protein;
	}

	public void setProtein(double protein) {
		this.protein = protein;
	}

	public double getFat() {
		return fat;
	}

	public void setFat(double fat) {
		this.fat = fat;
	}

	@Override
	public String toString() {
		return "FoodFile [num=" + num + ", foodName=" + foodName + ", servingSize=" + servingSize + ", kcal=" + kcal
				+ ", carbo=" + carbo + ", protein=" + protein + ", fat=" + fat + "]";
	}

}
