package com.troops.database;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import com.troops.model.Url;
import com.troops.model.UrlDetails;
import com.troops.util.FrequencyCount;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Sql2oDao {
	private static Sql2o sql2o;
	static final Logger logger = LoggerFactory.getLogger(Sql2oDao.class);

	static {
		logger.debug("Initializig DataSource using properties file");
		HikariConfig config = new HikariConfig("hikari.properties");
		//HikariConfig config = new HikariConfig("src/main/resources/hikari.properties");
		HikariDataSource ds = new HikariDataSource(config);
		sql2o = new Sql2o(ds);
	}

	public List<Url> getUrl(int id) {
		String sql = "SELECT id, url, fCountCode, createdOn " + "FROM urls " + "WHERE id=:id";
		List<Url> urls = null;
		try (Connection con = sql2o.open()) {
			logger.debug("creating and executing select url query");
			urls = con.createQuery(sql).addParameter("id", id).executeAndFetch(Url.class);
		}
		return urls;
	}

	public List<UrlDetails> getUrlDetails(int id) {
		String sql = "SELECT u.id, url, fCountCode, createdOn, clicks, lastAccessedOn "
				+ "FROM urls u JOIN urlclicks c " + "ON u.id = c.id " + "WHERE u.id=:id";
		try (Connection con = sql2o.open()) {
			logger.debug("creating and executing select url details query");
			return con.createQuery(sql).addParameter("id", id).executeAndFetch(UrlDetails.class);
		}
	}

	public int checkAndInsertOnFail(String url) {
		List<Url> urls = null;
		String fCountCode = FrequencyCount.getEncodedString(url);
		String sql = "SELECT id, fCountCode, createdOn, url " + "FROM urls " + "WHERE fCountCode=:fCountCode "
				+ "and url=:url";
		try (Connection con = sql2o.open()) {
			logger.debug("creating and executing select id query");
			urls = con.createQuery(sql).addParameter("fCountCode", fCountCode).addParameter("url", url)
					.executeAndFetch(Url.class);
		}
		int result = 0;
		if (urls.size() == 0) {
			logger.debug("No record found with given fCountCode and url");
			result = insert(url, fCountCode);
		} else if (urls.size() == 1) {
			logger.debug("Record found");
			result = urls.get(0).getId();
		} else {
			logger.error("Multiple records found");
			result = Integer.MIN_VALUE;
		}
		return result;
	}

	public int insert(String url, String fCountCode) {
		String sqlUrls = "INSERT INTO urls(url, fCountCode) values(:url, :fCountCode)";
		String sqlUrlClicks = "INSERT INTO urlclicks(id) values(:id)";
		long id = 0;
		try (Connection con = sql2o.beginTransaction()) {
			logger.debug("Transaction: Begin");
			id = (Long) con.createQuery(sqlUrls).addParameter("url", url).addParameter("fCountCode", fCountCode)
					.executeUpdate().getKey();
			con.createQuery(sqlUrlClicks).addParameter("id", id).executeUpdate();
			con.commit();
			logger.debug("Transaction: End");
		}
		return (int) id;
	}

	public void updateClicks(int id) {
		String sql = "UPDATE urlclicks " + "SET clicks = clicks + 1 " + "WHERE id = :id";
		try (Connection con = sql2o.open()) {
			logger.debug("Updating clicks");
			con.createQuery(sql).addParameter("id", id).executeUpdate();
		}
	}
}
