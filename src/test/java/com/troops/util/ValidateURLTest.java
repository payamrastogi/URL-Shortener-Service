package com.troops.util;

import org.junit.Assert;
import org.junit.Test;

import com.example.util.ValidateURL;

public class ValidateURLTest 
{
	@Test
	public void test()
	{
		//System.out.println(ValidateURL.validateLongURL("google.com"));
		Assert.assertTrue(ValidateURL.validateLongURL("google.com"));
		Assert.assertTrue(!ValidateURL.validateLongURL("http://google. com"));
	}
}
