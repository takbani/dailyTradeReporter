package com.reports.test.service;

import static java.math.BigDecimal.valueOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.doReturn;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.reports.model.TradingEntity;
import com.reports.service.DailyTradeReportService;
import com.reports.util.ReportServiceUtil;

@RunWith(MockitoJUnitRunner.class)
public class DailyTradeReportServiceTest {

	@Mock
	ReportServiceUtil reportServiceUtilMock;

	@Spy
	TradingEntity aedCurrenyBuyMock;

	@Spy
	TradingEntity sgpCurrenyBuyMock;

	@Spy
	TradingEntity inrCurrenySellMock;

	@Spy
	TradingEntity gbpCurrenySellMock;


	@InjectMocks
	DailyTradeReportService testObj;


	@Before
	public void init(){
		doReturn("AED").when(aedCurrenyBuyMock).getCurrency();
		doReturn("foo").when(aedCurrenyBuyMock).getEntityName();
		doReturn(100.00).when(aedCurrenyBuyMock).getUnits();
		doReturn(new Date()).when(aedCurrenyBuyMock).getInstructionDate();
		doReturn(true).when(aedCurrenyBuyMock).isBuyTrade();
		doReturn(new BigDecimal(0.22)).when(aedCurrenyBuyMock).getAgreedFx();
		doReturn(new BigDecimal(150.25)).when(aedCurrenyBuyMock).getPricePerUnit();

		doReturn("SGP").when(sgpCurrenyBuyMock).getCurrency();
		doReturn("bar").when(sgpCurrenyBuyMock).getEntityName();
		doReturn(150.00).when(sgpCurrenyBuyMock).getUnits();
		doReturn(new Date()).when(sgpCurrenyBuyMock).getInstructionDate();
		doReturn(true).when(sgpCurrenyBuyMock).isBuyTrade();
		doReturn(new BigDecimal(0.23)).when(sgpCurrenyBuyMock).getAgreedFx();
		doReturn(new BigDecimal(100.25)).when(sgpCurrenyBuyMock).getPricePerUnit();

		doReturn("INR").when(inrCurrenySellMock).getCurrency();
		doReturn("inrCompany").when(inrCurrenySellMock).getEntityName();
		doReturn(200.00).when(inrCurrenySellMock).getUnits();
		doReturn(new Date()).when(inrCurrenySellMock).getInstructionDate();
		doReturn(false).when(inrCurrenySellMock).isBuyTrade();
		doReturn(new BigDecimal(0.016)).when(inrCurrenySellMock).getAgreedFx();
		doReturn(new BigDecimal(170.25)).when(inrCurrenySellMock).getPricePerUnit();


		doReturn("GBP").when(gbpCurrenySellMock).getCurrency();
		doReturn("gbpCompany").when(gbpCurrenySellMock).getEntityName();
		doReturn(10.00).when(gbpCurrenySellMock).getUnits();
		doReturn(new Date()).when(gbpCurrenySellMock).getInstructionDate();
		doReturn(false).when(gbpCurrenySellMock).isBuyTrade();
		doReturn(new BigDecimal(1.16)).when(gbpCurrenySellMock).getAgreedFx();
		doReturn(new BigDecimal(50.25)).when(gbpCurrenySellMock).getPricePerUnit();

	}


	@Test
	public void givenValidDayForAEDBuyTradeSingleEntityReturnDailyReport(){
		List<TradingEntity> entityList= new ArrayList<>();
		doReturn(true).when(reportServiceUtilMock).isValidDayForSettlement(aedCurrenyBuyMock);
		doReturn(new DecimalFormat("0.00")).when(reportServiceUtilMock).getDecimalFormat();
		entityList.add(aedCurrenyBuyMock);
		testObj.getDailyReport(entityList);
		assertEquals(valueOf(0.00), testObj.getIncomingAmount());
		assertEquals(0,testObj.getSellList().size());
		assertEquals(1,testObj.getBuyList().size());
		assertNotEquals(valueOf(0.00), testObj.getOutgoingAmount());
	}


	@Test
	public void givenValidDayForAEDAndSGPBuyTradeReturnDailyReport(){
		List<TradingEntity> entityList= new ArrayList<>();
		doReturn(true).when(reportServiceUtilMock).isValidDayForSettlement(aedCurrenyBuyMock);
		doReturn(true).when(reportServiceUtilMock).isValidDayForSettlement(sgpCurrenyBuyMock);
		doReturn(new DecimalFormat("0.00")).when(reportServiceUtilMock).getDecimalFormat();
		entityList.add(aedCurrenyBuyMock);
		entityList.add(sgpCurrenyBuyMock);
		testObj.getDailyReport(entityList);
		assertEquals(valueOf(0.00), testObj.getIncomingAmount());
		assertEquals(0,testObj.getSellList().size());
		assertEquals(2,testObj.getBuyList().size());
		assertNotEquals(valueOf(0.00), testObj.getOutgoingAmount());
	}

	@Test
	public void givenInvalidDayForAEDAndSGPBuyReturnDailyReport(){
		List<TradingEntity> entityList= new ArrayList<>();
		doReturn(false).when(reportServiceUtilMock).isValidDayForSettlement(aedCurrenyBuyMock);
		doReturn(false).when(reportServiceUtilMock).isValidDayForSettlement(sgpCurrenyBuyMock);
		doReturn(new DecimalFormat("0.00")).when(reportServiceUtilMock).getDecimalFormat();
		entityList.add(aedCurrenyBuyMock);
		entityList.add(sgpCurrenyBuyMock);
		testObj.getDailyReport(entityList);
		assertEquals(valueOf(0.00), testObj.getIncomingAmount());
		assertEquals(0,testObj.getSellList().size());
		assertEquals(0,testObj.getBuyList().size());
		assertEquals(valueOf(0.00), testObj.getOutgoingAmount());
	}

	@Test
	public void givenInvalidDayForAEDAndValidDayForSGPBuyReturnDailyReport(){
		List<TradingEntity> entityList= new ArrayList<>();
		doReturn(false).when(reportServiceUtilMock).isValidDayForSettlement(aedCurrenyBuyMock);
		doReturn(true).when(reportServiceUtilMock).isValidDayForSettlement(sgpCurrenyBuyMock);
		doReturn(new DecimalFormat("0.00")).when(reportServiceUtilMock).getDecimalFormat();
		entityList.add(aedCurrenyBuyMock);
		entityList.add(sgpCurrenyBuyMock);
		testObj.getDailyReport(entityList);
		assertEquals(valueOf(0.00), testObj.getIncomingAmount());
		assertEquals(0,testObj.getSellList().size());
		assertEquals(1,testObj.getBuyList().size());
		assertNotEquals(valueOf(0.00), testObj.getOutgoingAmount());
	}


	@Test
	public void givenTwoSellEntityOnValidDaysThenReturnDailyReport(){
		List<TradingEntity> entityList= new ArrayList<>();
		doReturn(true).when(reportServiceUtilMock).isValidDayForSettlement(inrCurrenySellMock);
		doReturn(true).when(reportServiceUtilMock).isValidDayForSettlement(gbpCurrenySellMock);
		doReturn(new DecimalFormat("0.00")).when(reportServiceUtilMock).getDecimalFormat();
		entityList.add(inrCurrenySellMock);
		entityList.add(gbpCurrenySellMock);
		testObj.getDailyReport(entityList);
		assertNotEquals(valueOf(0.00), testObj.getIncomingAmount());
		assertEquals(2,testObj.getSellList().size());
		assertEquals(0,testObj.getBuyList().size());
		assertEquals(valueOf(0.00), testObj.getOutgoingAmount());
	}

	@Test
	public void givenTwoBuyAndOneSellEntityOnValidDaysThenReturnDailyReport(){
		List<TradingEntity> entityList= new ArrayList<>();
		doReturn(true).when(reportServiceUtilMock).isValidDayForSettlement(aedCurrenyBuyMock);
		doReturn(true).when(reportServiceUtilMock).isValidDayForSettlement(sgpCurrenyBuyMock);
		doReturn(true).when(reportServiceUtilMock).isValidDayForSettlement(inrCurrenySellMock);
		doReturn(new DecimalFormat("0.00")).when(reportServiceUtilMock).getDecimalFormat();
		entityList.add(aedCurrenyBuyMock);
		entityList.add(sgpCurrenyBuyMock);
		entityList.add(inrCurrenySellMock);
		testObj.getDailyReport(entityList);
		assertNotEquals(valueOf(0.00), testObj.getIncomingAmount());
		assertEquals(1,testObj.getSellList().size());
		assertEquals(2,testObj.getBuyList().size());
		assertNotEquals(valueOf(0.00), testObj.getOutgoingAmount());
	}


	@Test
	public void givenTwoBuyAndTwoSellEntityOnValidDaysThenReturnDailyReport(){
		List<TradingEntity> entityList= new ArrayList<>();
		doReturn(true).when(reportServiceUtilMock).isValidDayForSettlement(aedCurrenyBuyMock);
		doReturn(true).when(reportServiceUtilMock).isValidDayForSettlement(sgpCurrenyBuyMock);
		doReturn(true).when(reportServiceUtilMock).isValidDayForSettlement(inrCurrenySellMock);
		doReturn(true).when(reportServiceUtilMock).isValidDayForSettlement(gbpCurrenySellMock);
		doReturn(new DecimalFormat("0.00")).when(reportServiceUtilMock).getDecimalFormat();
		entityList.add(aedCurrenyBuyMock);
		entityList.add(sgpCurrenyBuyMock);
		entityList.add(inrCurrenySellMock);
		entityList.add(gbpCurrenySellMock);
		testObj.getDailyReport(entityList);
		assertNotEquals(valueOf(0.00), testObj.getIncomingAmount());
		assertEquals(2,testObj.getSellList().size());
		assertEquals(2,testObj.getBuyList().size());
		assertNotEquals(valueOf(0.00), testObj.getOutgoingAmount());
	}

	@Test
	public void givenOneBuyEntityOnValidDayAndOneSellEntityOnInValidDayThenReturnDailyReport(){
		List<TradingEntity> entityList= new ArrayList<>();
		doReturn(true).when(reportServiceUtilMock).isValidDayForSettlement(sgpCurrenyBuyMock);
		doReturn(false).when(reportServiceUtilMock).isValidDayForSettlement(gbpCurrenySellMock);
		doReturn(new DecimalFormat("0.00")).when(reportServiceUtilMock).getDecimalFormat();
		entityList.add(sgpCurrenyBuyMock);
		entityList.add(gbpCurrenySellMock);
		testObj.getDailyReport(entityList);
		assertEquals(valueOf(0.00), testObj.getIncomingAmount());
		assertEquals(0,testObj.getSellList().size());
		assertEquals(1,testObj.getBuyList().size());
		assertNotEquals(valueOf(0.00), testObj.getOutgoingAmount());
	} 

}
