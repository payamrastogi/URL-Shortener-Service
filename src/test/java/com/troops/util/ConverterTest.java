package com.troops.util;

import org.junit.Test;
import com.troops.util.Convert;

import org.junit.Assert;

public class ConverterTest 
{
	@Test
	public void test()
	{
		Assert.assertEquals(1, Convert.shortURLtoID("b"));
		
		Assert.assertEquals("bEn", Convert.idToShortURL(5000));
		Assert.assertEquals("99", Convert.idToShortURL(3363));
		Assert.assertEquals("kqpv", Convert.idToShortURL(1998989));
		Assert.assertEquals("dQ1", Convert.idToShortURL(12345));
		
		Assert.assertEquals(5000, Convert.shortURLtoID("bEn"));
		Assert.assertEquals(3907, Convert.shortURLtoID("bjx"));
		Assert.assertEquals(1998989, Convert.shortURLtoID("kqpv"));
		Assert.assertEquals(12345, Convert.shortURLtoID("dQ1"));
		Assert.assertEquals(1, Convert.shortURLtoID("aab"));
	}
}
	