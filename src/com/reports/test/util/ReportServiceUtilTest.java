package com.reports.test.util;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;

import java.util.GregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.reports.model.TradingEntity;
import com.reports.util.ReportServiceUtil;

@RunWith(MockitoJUnitRunner.class)
public class ReportServiceUtilTest {
	
	@Mock
	TradingEntity tradingEntityMock;

	@InjectMocks
	ReportServiceUtil testObj= new ReportServiceUtil();
	
	@Test
	public void givenAEDCurrencyAndDayAsThursdayReturnTrue(){
		doReturn( new GregorianCalendar(2017, 06, 12).getTime()).when(tradingEntityMock).getInstructionDate();
		doReturn("AED").when(tradingEntityMock).getCurrency();
		assertTrue(testObj.isValidDayForSettlement(tradingEntityMock));
	}
	
	@Test
	public void givenSARCurrencyAndDayAsFridayReturnFalse(){
		doReturn( new GregorianCalendar(2017, 06, 14).getTime()).when(tradingEntityMock).getInstructionDate();
		doReturn("SAR").when(tradingEntityMock).getCurrency();
		assertFalse(testObj.isValidDayForSettlement(tradingEntityMock));
	}
	
	@Test
	public void givenINRCurrencyAndDayAsThursdayReturnTrue(){
		doReturn( new GregorianCalendar(2017, 06, 12).getTime()).when(tradingEntityMock).getInstructionDate();
		doReturn("INR").when(tradingEntityMock).getCurrency();
		assertTrue(testObj.isValidDayForSettlement(tradingEntityMock));
	}
	
	@Test
	public void givenINRCurrencyAndDayAsSaturdayReturnFalse(){
		doReturn( new GregorianCalendar(2017, 06, 15).getTime()).when(tradingEntityMock).getInstructionDate();
		doReturn("INR").when(tradingEntityMock).getCurrency();
		assertFalse(testObj.isValidDayForSettlement(tradingEntityMock));
	}
	
	@Test
	public void givenINRCurrencyAndDayAsFridayReturnTrue(){
		doReturn( new GregorianCalendar(2017, 06, 14).getTime()).when(tradingEntityMock).getInstructionDate();
		doReturn("INR").when(tradingEntityMock).getCurrency();
		assertTrue(testObj.isValidDayForSettlement(tradingEntityMock));
	}
}
