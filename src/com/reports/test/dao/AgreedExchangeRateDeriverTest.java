package com.reports.test.dao;

import static com.reports.dao.AgreedExchangeRateDeriver.getAgreedExchangeRate;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.reports.model.TradingEntity;

@RunWith(MockitoJUnitRunner.class)
public class AgreedExchangeRateDeriverTest {

	@Mock
	TradingEntity entityMock;

	@Test
	public void testAgreedFxForBarAndAED(){
		doReturn("AED").when(entityMock).getCurrency();
		doReturn("foo").when(entityMock).getEntityName();
		BigDecimal agreedFx=getAgreedExchangeRate(entityMock.getEntityName()+"_"+entityMock.getCurrency());
		assertEquals(BigDecimal.valueOf(0.23),agreedFx);
	}
	
	@Test
	public void testAgreedFxForFooAndAED(){
		doReturn("AED").when(entityMock).getCurrency();
		doReturn("bar").when(entityMock).getEntityName();
		BigDecimal agreedFx=getAgreedExchangeRate(entityMock.getEntityName()+"_"+entityMock.getCurrency());
		assertEquals(BigDecimal.valueOf(0.22),agreedFx);
	}
}
