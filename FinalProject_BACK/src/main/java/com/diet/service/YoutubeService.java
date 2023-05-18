package com.diet.service;

import java.util.List;

import com.diet.model.dto.YoutubeVideo;

public interface YoutubeService {
	public List<YoutubeVideo> searchVideos(String query, int maxResults);
}
