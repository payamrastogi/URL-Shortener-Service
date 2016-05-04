package com.troops.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Convert 
{	
	static final Logger logger = LoggerFactory.getLogger(Convert.class);
	//iIlLoO01
	//omitting l,o, I, O to avoid the confusing
	private static final String map = "abcdefghijkmnpqrstuvwxyz"//omitting l, o
			+ "ABCDEFGHJKLMNPQRSTUVWXYZ" //omitting I, O
			+ "0123456789";
	
	public static String idToShortURL(int id)
	{
		logger.debug("id: " + id);
		StringBuilder shortURL = new StringBuilder();
		while(id>0)
		{
			shortURL.insert(0, map.charAt(id%58));
			id /= 58;
		}
		logger.debug("shortURL: " + shortURL.toString());
		return shortURL.toString();
	}
	
	public static int shortURLtoID(String shortURL)
	{
		logger.debug("shortURL: " + shortURL);
		int id = 0;
		for(int i=0;i<shortURL.length();i++)
		{
			char character = shortURL.charAt(i);
			if(map.contains(character+""))
				id = (id*58) + map.indexOf(character);
		}
		logger.debug("id: " + id);
		return id;
	}
	
	public static String toJson(Object data) 
	{
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String jsonString=null;
		try 
		{
			jsonString = mapper.writeValueAsString(data);
		} 
		catch (JsonProcessingException e) 
		{
			logger.error("JsonProcessingException: "+e.getMessage());
			throw new RuntimeException("JsonProcessingException");
		}
        return jsonString;
    }
	
	public static String toJson(String key, String value)
	{
		Map<String, String> map = new HashMap<>();
		map.put(key, value);
		return toJson(map);
	}
}
