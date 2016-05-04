package com.troops.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrequencyCount {
	static final Logger logger = LoggerFactory.getLogger(FrequencyCount.class);
	// map of permissible characters in URL
	private static final String map = "abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789"
			+ "$-_.+!*'(),;/?:@=&%";

	public static String getEncodedString(String url) {
		int[] countArray = new int[81];
		StringBuilder code = new StringBuilder();
		for (int i = 0; i < url.length(); i++) {
			char character = url.charAt(i);
			countArray[map.indexOf(character)]++;
		}
		for (int i = 0; i < countArray.length; i++) {
			logger.debug(countArray[i] % 10 + "");
			code.append((countArray[i] % 10) + "");
		}
		logger.debug("fCountCode length: " + code.toString().length());
		return code.toString();
	}
}
