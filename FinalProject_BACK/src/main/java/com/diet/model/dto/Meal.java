package com.diet.model.dto;

public class Meal {
	private int mealId;
	private String userId;
	private String menuName;
	private double intake;
	private double menuKcal;
	private double carbo;
	private double protein;
	private double fat;
	private String eatingTime;
	private int divideTime;
	private String mealImg;

	public Meal() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getMealImg() {
		return mealImg;
	}

	public void setMealImg(String mealImg) {
		this.mealImg = mealImg;
	}

	public int getMealId() {
		return mealId;
	}

	public void setMealId(int mealId) {
		this.mealId = mealId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public double getIntake() {
		return intake;
	}

	public void setIntake(double intake) {
		this.intake = intake;
	}

	public double getMenuKcal() {
		return menuKcal;
	}

	public void setMenuKcal(double menuKcal) {
		this.menuKcal = menuKcal;
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

	public String getEatingTime() {
		return eatingTime;
	}

	public void setEatingTime(String eatingTime) {
		this.eatingTime = eatingTime;
	}

	public int getDivideTime() {
		return divideTime;
	}

	public void setDivideTime(int divideTime) {
		this.divideTime = divideTime;
	}

	@Override
	public String toString() {
		return "Meal [mealId=" + mealId + ", userId=" + userId + ", menuName=" + menuName + ", intake=" + intake
				+ ", menuKcal=" + menuKcal + ", carbo=" + carbo + ", protein=" + protein + ", fat=" + fat
				+ ", eatingTime=" + eatingTime + ", divideTime=" + divideTime + ", mealImg=" + mealImg + "]";
	}


}
