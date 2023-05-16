package com.diet.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtils {

	// 파일 압축
	public static byte[] compressImg(byte[] imageFile) {
		Deflater deflater = new Deflater();
		deflater.setLevel(deflater.BEST_COMPRESSION);
		deflater.setInput(imageFile);
		deflater.finish();
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageFile.length);
		byte[] tmp = new byte[2 * 1024];
		
		while(!deflater.finished()) {
			int size = deflater.deflate(tmp);
			outputStream.write(tmp, 0, size);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return outputStream.toByteArray();
	}
	
	// 압축 해제
	public static byte[] decompressImg(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] tmp = new byte[2 * 1024];
		
		try {
			while(!inflater.finished()) {
				int size = inflater.inflate(tmp);
				outputStream.write(tmp, 0, size);
			}
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputStream.toByteArray();
	}

}
