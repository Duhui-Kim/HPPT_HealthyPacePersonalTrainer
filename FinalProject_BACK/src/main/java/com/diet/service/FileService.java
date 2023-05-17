package com.diet.service;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.diet.model.dto.ImgFile;

public interface FileService {
	public ImgFile getFileData(String fileName);
	
	public String upload(MultipartFile uploadFile) throws IOException;

	public byte[] download(String fileName);
}
