package com.diet.model.dto;

public class FoodAPI {
	// 번호
	private String NUM;
	// 식품명
	private String DESC_KOR;
	// 총 내용량
	private String SERVING_SIZE = "0";
	// 열량(kcal)
	private String NUTR_CONT1 = "0";
	// 탄수화물
	private String NUTR_CONT2 = "0";
	// 단백질
	private String NUTR_CONT3 = "0";
	// 지방
	private String NUTR_CONT4 = "0";

	public FoodAPI() {
	}

	public FoodAPI(String nUM, String dESC_KOR, String sERVING_SIZE, String nUTR_CONT1, String nUTR_CONT2,
			String nUTR_CONT3, String nUTR_CONT4) {
		super();
		NUM = nUM;
		DESC_KOR = dESC_KOR;
		SERVING_SIZE = sERVING_SIZE;
		NUTR_CONT1 = nUTR_CONT1;
		NUTR_CONT2 = nUTR_CONT2;
		NUTR_CONT3 = nUTR_CONT3;
		NUTR_CONT4 = nUTR_CONT4;
	}

	public String getNUM() {
		return NUM;
	}

	public void setNUM(String nUM) {
		NUM = nUM;
	}

	public String getDESC_KOR() {
		return DESC_KOR;
	}

	public void setDESC_KOR(String dESC_KOR) {
		DESC_KOR = dESC_KOR;
	}

	public String getSERVING_SIZE() {
		return SERVING_SIZE;
	}

	public void setSERVING_SIZE(String sERVING_SIZE) {
		SERVING_SIZE = sERVING_SIZE;
	}

	public String getNUTR_CONT1() {
		return NUTR_CONT1;
	}

	public void setNUTR_CONT1(String nUTR_CONT1) {
		NUTR_CONT1 = nUTR_CONT1;
	}

	public String getNUTR_CONT2() {
		return NUTR_CONT2;
	}

	public void setNUTR_CONT2(String nUTR_CONT2) {
		NUTR_CONT2 = nUTR_CONT2;
	}

	public String getNUTR_CONT3() {
		return NUTR_CONT3;
	}

	public void setNUTR_CONT3(String nUTR_CONT3) {
		NUTR_CONT3 = nUTR_CONT3;
	}

	public String getNUTR_CONT4() {
		return NUTR_CONT4;
	}

	public void setNUTR_CONT4(String nUTR_CONT4) {
		NUTR_CONT4 = nUTR_CONT4;
	}

	@Override
	public String toString() {
		return "Food [NUM=" + NUM + ", DESC_KOR=" + DESC_KOR + ", SERVING_SIZE=" + SERVING_SIZE + ", NUTR_CONT1="
				+ NUTR_CONT1 + ", NUTR_CONT2=" + NUTR_CONT2 + ", NUTR_CONT3=" + NUTR_CONT3 + ", NUTR_CONT4="
				+ NUTR_CONT4 + "]";
	}

}
