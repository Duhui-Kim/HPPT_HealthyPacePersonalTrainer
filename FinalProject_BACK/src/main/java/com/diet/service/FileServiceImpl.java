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

import com.diet.model.dao.ImgFileDao;
import com.diet.model.dto.ImgFile;
import com.diet.utils.ImageUtils;

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	private ImgFileDao userImgDao;
	
	// 파일 업로드 후 저장된 경로 반환
	@Override
	@Transactional
	public String upload(MultipartFile uploadFile) {				
		ImgFile imgFile = new ImgFile();
				
		try {
			// 파일이름 겹치지 않게 저장
			int idx = uploadFile.getOriginalFilename().lastIndexOf('.');
			String fileName = UUID.randomUUID() + uploadFile.getOriginalFilename().substring(idx);
			
			imgFile.setFileName(fileName);
			imgFile.setType(uploadFile.getContentType());
			imgFile.setImageData(ImageUtils.compressImg(uploadFile.getBytes()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		userImgDao.insertFile(imgFile);
		
		return imgFile.getFileName();
	}

	// 파일이름으로 검색하여 파일 전달
	@Override
	public byte[] download(String fileName) {
		ImgFile imgFile = userImgDao.selectFile(fileName);
		
		if(imgFile == null) 
			return null;
		else 
			return ImageUtils.decompressImg(imgFile.getImageData());
	}

	// 파일 정보 가져오기
	@Override
	public ImgFile getFileData(String fileName) {
		return userImgDao.selectFile(fileName);
	}

}
