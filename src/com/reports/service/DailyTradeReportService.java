package com.reports.service;

import static java.lang.String.format;
import static java.math.BigDecimal.valueOf;
import static java.util.Collections.sort;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.reports.model.TradingEntity;
import com.reports.util.ReportServiceUtil;

public class DailyTradeReportService {


	private static final String SELL = "sell";

	private static final String BUY = "buy";

	public static Logger LOGGER=Logger.getLogger(DailyTradeReportService.class.getName());

	private ReportServiceUtil reportServiceUtil= new ReportServiceUtil();

	BigDecimal outgoingAmount = valueOf(0.00);

	BigDecimal incomingAmount = valueOf(0.00);

	List<TradingEntity> buyList= new ArrayList<>();

	List<TradingEntity> sellList= new ArrayList<>();


	public void getDailyReport(List<TradingEntity> entityList){
		buyList=populateAndSortOutgoingList(entityList, BUY);
		sellList=populateAndSortIncomingList(entityList, SELL);
		if(!sellList.isEmpty() && !incomingAmount.equals(valueOf(0.00))){
			LOGGER.log(Level.INFO,()->format("Total incoming amount settled is %s",getReportServiceUtil().getDecimalFormat().format(incomingAmount)));
			LOGGER.log(Level.INFO,()->format("The entity with the highest incoming amount is %s.The amount is %s. The settlement happend on %s",sellList.get(0).getEntityName(),sellList.get(0).getTotalAmount(),sellList.get(0).getSettlementDate()));
			if(sellList.size()>1){
				LOGGER.log(Level.INFO,()->format("The entity with the lowest incoming amount is %s.The amount is %s.  The settlement happend on %s",sellList.get(sellList.size()-1).getEntityName(),sellList.get(sellList.size()-1).getTotalAmount(),sellList.get(sellList.size()-1).getSettlementDate()));
			}
		}
		else if(incomingAmount.equals(valueOf(0.00))&& !sellList.isEmpty()){
			LOGGER.log(Level.INFO,()->format("There are no incoming settlements today due to it being non working day for the currency %s",sellList.get(0).getCurrency()));
		}
		else{
			LOGGER.info("There are no incoming transactions today.");
		}
		if(!buyList.isEmpty()&& !outgoingAmount.equals(valueOf(0.00))){
			LOGGER.log(Level.INFO,()->format("Total outgoing amount settled is %s",getReportServiceUtil().getDecimalFormat().format(outgoingAmount)));
			LOGGER.log(Level.INFO,()->format("The entity with the highest outgoing amount is %s. The amount is %s. The settlement happend on %s",buyList.get(0).getEntityName(),buyList.get(0).getTotalAmount(),buyList.get(0).getSettlementDate()));
			if(buyList.size()>1){
				LOGGER.log(Level.INFO,()->format("The entity with the lowest outgoing amount is %s. The amount is %s.  The settlement happend on %s",buyList.get(buyList.size()-1).getEntityName(),buyList.get(buyList.size()-1).getTotalAmount(),buyList.get(buyList.size()-1).getSettlementDate()));
			}
		}
		else if(outgoingAmount.equals(valueOf(0.00))&& !buyList.isEmpty()){
			LOGGER.log(Level.INFO,()->format("There are no outgoing settlements today due to it being non working day for the currency %s",buyList.get(0).getCurrency()));
		}
		else{
			LOGGER.info("There are no outgoing transactions today.");
		}
	}


	private List<TradingEntity> populateAndSortIncomingList(List<TradingEntity> entityList,String status) {
		List<TradingEntity> incomingList=entityList.stream().filter(t->!t.isBuyTrade()).collect(toList());
		return validateAndSortListByTotalAmount(status, incomingList);
	}


	private List<TradingEntity> populateAndSortOutgoingList(List<TradingEntity> entityList,String status) {
		List<TradingEntity> outgoingList=entityList.stream().filter(TradingEntity::isBuyTrade).collect(toList());
		return validateAndSortListByTotalAmount(status, outgoingList);
	}


	private List<TradingEntity> validateAndSortListByTotalAmount(String status,
			List<TradingEntity> entityList) {
		entityList.stream().forEach(t->validateDayAndSetTotalAmtOnEntity(status, t));
		entityList.removeIf(t->t.getTotalAmount()==null);
		sort(entityList,(t1,t2)->t2.getTotalAmount().compareTo(t1.getTotalAmount()));
		return entityList;
	}



	private void validateDayAndSetTotalAmtOnEntity(String status,
			TradingEntity t) {
		boolean isValidSettlementDay=getReportServiceUtil().isValidDayForSettlement(t);
		if(isValidSettlementDay){
			BigDecimal amtForEntity=getAmountSettledForIndividualEntity(t);
			if(status.equalsIgnoreCase(BUY)){
				outgoingAmount=outgoingAmount.add(amtForEntity);
			}
			else{
				incomingAmount=incomingAmount.add(amtForEntity);
			}
			t.setTotalAmount(amtForEntity);
			t.setSettlementDate(t.getInstructionDate());
		}
	}


	public BigDecimal getAmountSettledForIndividualEntity(TradingEntity entity){
		if(nonNull(entity.getAgreedFx())&&nonNull(entity.getPricePerUnit())&&nonNull(entity.getUnits())){
			return entity.getAgreedFx().multiply(entity.getPricePerUnit()).multiply(valueOf(entity.getUnits())).setScale(2,RoundingMode.CEILING);
		}
		return null;
	}



	public ReportServiceUtil getReportServiceUtil() {
		return reportServiceUtil;
	}



	public void setReportServiceUtil(ReportServiceUtil reportServiceUtil) {
		this.reportServiceUtil = reportServiceUtil;
	}


	public BigDecimal getOutgoingAmount() {
		return outgoingAmount;
	}


	public void setOutgoingAmount(BigDecimal outgoingAmount) {
		this.outgoingAmount = outgoingAmount;
	}


	public BigDecimal getIncomingAmount() {
		return incomingAmount;
	}


	public void setIncomingAmount(BigDecimal incomingAmount) {
		this.incomingAmount = incomingAmount;
	}


	public List<TradingEntity> getBuyList() {
		return buyList;
	}


	public void setBuyList(List<TradingEntity> buyList) {
		this.buyList = buyList;
	}


	public List<TradingEntity> getSellList() {
		return sellList;
	}


	public void setSellList(List<TradingEntity> sellList) {
		this.sellList = sellList;
	}



}
