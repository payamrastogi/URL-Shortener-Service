package com.troops.util;

import org.junit.Test;
import org.junit.Assert;

public class FrequencyCountTest 
{
	@Test
	public void test()
	{
		String url = "https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&cad=rja&uact=8&ved=0ahUKEwjn2JW08bfMAhWBMSYKHQTrCq8QFggcMAA&url=http%3A%2F%2Fwww.nurkiewicz.com%2F2014%2F08%2Furl-shortener-service-in-42-lines-of.html&usg=AFQjCNFFSkNsGR39HMu8qw_CysfNHB3T-Q&sig2=SLPtI_0BH9Ok29QRcwnGRQ&bvm=bv.121070826,d.eWE";
		Assert.assertEquals("542423666436467232208410115330292411214311633210301074232011630727000000103110326", FrequencyCount.getEncodedString(url));
	}
}
