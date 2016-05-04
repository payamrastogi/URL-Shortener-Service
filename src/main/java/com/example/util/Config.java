package com.example.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	private String hostname;
	private String port;

	public Config(File configFile) {
		this(loadProperties(configFile));
	}

	private static Properties loadProperties(File file) {
		FileInputStream is = null;
		try {
			is = new FileInputStream(file);
			return loadProperties(is);
		} catch (IOException e) {
			throw new RuntimeException("Error loading property file " + file.getAbsolutePath(), e);
		} finally {
			CloseUtil.close(is);
		}
	}

	Config(Properties prop) {
		this.hostname = prop.getProperty("hostname");
		this.port = prop.getProperty("port");
	}

	private static Properties loadProperties(InputStream is) {
		Properties prop = new Properties();
		try {
			prop.load(is);
		} catch (IOException e) {
			throw new RuntimeException("Error loading property from stream", e);
		}
		return prop;
	}

	public String getHostname() {
		return this.hostname;
	}

	public String getPort() {
		return this.port;
	}

	public String getURL() {
		return "http://" + this.hostname + ":" + this.port + "/";
	}
}
