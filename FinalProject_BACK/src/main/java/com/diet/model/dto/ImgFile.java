package com.diet.model.dto;

import java.util.Arrays;

public class ImgFile {
	private String fileName;
	private String type;
	private byte[] imageData;
	
	public ImgFile() {
	}

	public ImgFile(String fileName, String type, byte[] imageData) {
		super();
		this.fileName = fileName;
		this.type = type;
		this.imageData = imageData;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	@Override
	public String toString() {
		return "UserImg [fileName=" + fileName + ", type=" + type + ", imageData=" + Arrays.toString(imageData) + "]";
	}
}
