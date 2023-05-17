package com.diet.model.dao;

import com.diet.model.dto.ImgFile;

public interface ImgFileDao {
	public int insertFile(ImgFile userImg);
	
	public ImgFile selectFile(String fileName);
}
