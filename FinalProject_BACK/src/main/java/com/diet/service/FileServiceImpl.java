package com.diet.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.diet.model.dao.UserImgDao;
import com.diet.model.dto.UserImg;
import com.diet.utils.ImageUtils;

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	private UserImgDao userImgDao;
	
	// 파일 업로드 후 저장된 경로 반환
	@Override
	@Transactional
	public String upload(MultipartFile uploadFile) {				
		UserImg userImg = new UserImg();
				
		try {
			// 파일이름 겹치지 않게 저장
			int idx = uploadFile.getOriginalFilename().lastIndexOf('.');
			String fileName = UUID.randomUUID() + uploadFile.getOriginalFilename().substring(idx);
			
			userImg.setFileName(fileName);
			userImg.setType(uploadFile.getContentType());
			userImg.setImageData(ImageUtils.compressImg(uploadFile.getBytes()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		userImgDao.insertFile(userImg);
		
		return userImg.getFileName();
	}

	// 파일 전달
	@Override
	public byte[] download(String fileName) {
		UserImg userImg = userImgDao.selectFile(fileName);
		
		if(userImg == null) 
			return null;
		else 
			return ImageUtils.decompressImg(userImg.getImageData());
	}

	// 파일 정보 가져오기
	@Override
	public UserImg getFileData(String fileName) {
		return userImgDao.selectFile(fileName);
	}

}
