package com.diet.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.diet.model.dto.YoutubeVideo;

@Service
public class YoutubeServiceImpl implements YoutubeService {

	@Value("${youtube.api.key}")
	private String youtubeApiKey;
	
	@Override
	public List<YoutubeVideo> searchVideos(String query, int maxResults) {
		StringBuilder sb = new StringBuilder();

		try {
			URL url = new URL("https://www.googleapis.com/youtube/v3/search" + "?part=snippet&maxResults=" + maxResults
					+ "&q=" + query + "&key=" + youtubeApiKey + "&type=video");	
			
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;

			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			// JSON 형식의 데이터를 Array로 가져옴
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
			JSONArray jsonArray = (JSONArray) jsonObject.get("items");

			List<YoutubeVideo> videos = new ArrayList<>();

			for (Object obj : jsonArray) {
				YoutubeVideo video = new YoutubeVideo();
				Object id = ((JSONObject) obj).get("id");

				video.setVideoId(((JSONObject) id).get("videoId").toString());

				Object snp = ((JSONObject) obj).get("snippet");

				video.setTitle(((JSONObject) snp).get("title").toString());

				video.setDescription(((JSONObject) snp).get("description").toString());

				Object thnls = ((JSONObject) snp).get("thumbnails");
				Object dft = ((JSONObject) thnls).get("default");
				video.setThumbnailUrl(((JSONObject) dft).get("url").toString());

				video.setPublishedAt(((JSONObject) snp).get("publishedAt").toString());

				videos.add(video);
			}
			return videos;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
