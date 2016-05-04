package com.troops.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidateURL {
	static final Logger logger = LoggerFactory.getLogger(ValidateURL.class);

	public static boolean validateLongURL(String url) {
		boolean foundMatch = false;
		try {
			Pattern regex = Pattern.compile(
					"\\b(?:(https?|ftp|file)://|www\\.)?[-A-Z0-9+&#/%?=~_|$!:,.;]*[A-Z0-9+&@#/%=~_|$]\\.[-A-Z0-9+&@#/%?=~_|$!:,.;]*[A-Z0-9+&@#/%=~_|$]",
					Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
			Matcher regexMatcher = regex.matcher(url);
			foundMatch = regexMatcher.matches();
		} catch (PatternSyntaxException e) {
			logger.error("Syntax error in the regular expression\n" + e.getMessage());
		}
		return foundMatch;
	}

	public static boolean validateShortURL(String url) {
		for (int i = 0; i < url.length(); i++) {
			char c = url.charAt(i);
			if (c != 'l' && c != 'o' && c != 'I' && c != 'O') {
				if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')) {
					continue;
				} else {
					logger.error("Invalid character found in the ShortURL:" + c);
					return false;
				}
			} else {
				logger.error("Invalid character found in the ShortURL:" + c);
				return false;
			}
		}
		return true;
	}
}
