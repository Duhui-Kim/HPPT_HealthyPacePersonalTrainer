package com.diet.model.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "유튜브 영상 정보")
public class YoutubeVideo {
	private String videoId;
	private String title;
	private String description;
	private String thumbnailUrl;
	private String publishedAt;

	public YoutubeVideo() {

	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}

	@Override
	public String toString() {
		return "YoutubeVideo [videoId=" + videoId + ", title=" + title + ", description=" + description
				+ ", thumbnailUrl=" + thumbnailUrl + ", publishedAt=" + publishedAt + "]";
	}

}
