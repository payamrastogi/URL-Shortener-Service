package com.example.service;

import static spark.Spark.get;
import static spark.Spark.post;

import java.io.File;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.database.ClickUpdater;
import com.example.database.Sql2oDao;
import com.example.model.Url;
import com.example.model.UrlDetails;
import com.example.util.Config;
import com.example.util.Convert;
import com.example.util.ValidateURL;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class URLShortenerService {

	static final Logger logger = LoggerFactory.getLogger(URLShortenerService.class);

	private static final int HTTP_BAD_REQUEST = 400;
	private static final int HTTP_OK = 200;

	private static final BlockingQueue<Integer> idQueue = new ArrayBlockingQueue<Integer>(1000);

	public static void main(String[] args) {
		File file = new File("config.properties");
		//File file = new File("src/main/resources/config.properties");
		if (!file.exists()) {
			System.out.println("config.properties not found @" + file.getAbsolutePath());
			System.exit(1);
		}
		Config config = new Config(file);
		String hostUrl = config.getURL();
		// Create an endpoint that takes in a URL and shortens in to a
		// reasonable size
		post("/shorten", (request, response) -> {
			Url url = null;
			String resp = null;
			response.type("application/json");
			try {
				ObjectMapper mapper = new ObjectMapper();
				url = mapper.readValue(request.body(), Url.class);
				if (ValidateURL.validateLongURL(url.getUrl())) {
					Sql2oDao sql2oDao = new Sql2oDao();
					int id = sql2oDao.checkAndInsertOnFail(url.getUrl());
					logger.debug("Request succeeded");
					response.status(HTTP_OK);
					resp = Convert.toJson("ShortURL", hostUrl + Convert.idToShortURL(id));
				} else {
					logger.error("Invalid URL provided");
					response.status(HTTP_BAD_REQUEST);
					resp = Convert.toJson("Error", "Invalid URL");
				}
			} catch (JsonParseException e) {
				logger.error("Exception in parsing Json");
				response.status(HTTP_BAD_REQUEST);
				resp = Convert.toJson("Error", "Invalid URL");
			}
			return resp;
		});

		// Create an endpoint that takes in a short URL and returns the long URL
		get("/:shortened", (request, response) -> {
			String shortened = request.params(":shortened");
			response.type("application/json");
			String resp = null;
			if (ValidateURL.validateShortURL(shortened)) {
				int id = Convert.shortURLtoID(shortened);
				idQueue.put(id);
				Sql2oDao sql2oDao = new Sql2oDao();
				List<Url> urls = sql2oDao.getUrl(id);
				if (urls.size() != 0) {
					logger.debug("Request succeeded");
					response.status(HTTP_OK);
					response.type("application/json");
					resp = Convert.toJson(urls.get(0));
				} else {
					logger.error("Short URL does not exist in database");
					response.status(HTTP_BAD_REQUEST);
					resp = Convert.toJson("Error", "Short URL does not exist");
				}
			} else {
				logger.error("Invalid Short URL");
				response.status(HTTP_BAD_REQUEST);
				resp = Convert.toJson("Error", "Invalid Short URL");
			}
			return resp;
		});

		// Create a tracking mechanism to track the number of “clicks” on a
		// particular short URL
		get("/getclicks/:shortened", (request, response) -> {
			String shortened = request.params(":shortened");
			response.type("application/json");
			String resp = null;
			if (ValidateURL.validateShortURL(shortened)) {
				int id = Convert.shortURLtoID(shortened);
				Sql2oDao sql2oDao = new Sql2oDao();
				List<UrlDetails> urlDetails = sql2oDao.getUrlDetails(id);
				if (urlDetails.size() != 0) {
					response.status(HTTP_OK);
					resp = Convert.toJson(urlDetails.get(0));
				} else {
					response.status(HTTP_BAD_REQUEST);
					resp = Convert.toJson("Error", "Short URL does not exist");
				}
			} else {
				response.status(HTTP_BAD_REQUEST);
				resp = Convert.toJson("Error", "Invalid Short URL");
			}
			return resp;
		});
		new Thread(new ClickUpdater(idQueue)).start();
	}
}
