package com.reports.model;

import static com.reports.dao.AgreedExchangeRateDeriver.getAgreedExchangeRate;
import static java.util.Objects.nonNull;

import java.math.BigDecimal;
import java.util.Date;

public class TradingEntity {

	private String entityName;

	private BigDecimal pricePerUnit;

	private double units;

	private Date instructionDate;

	private Date settlementDate;

	private BigDecimal totalAmount;

	private String currency;

	private boolean isBuyTrade;

	public BigDecimal getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(BigDecimal pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public boolean isBuyTrade() {
		return isBuyTrade;
	}

	public void setBuyTrade(boolean isBuyTrade) {
		this.isBuyTrade = isBuyTrade;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public double getUnits() {
		return units;
	}

	public void setUnits(double units) {
		this.units = units;
	}

	public Date getInstructionDate() {
		return instructionDate;
	}

	public void setInstructionDate(Date instructionDate) {
		this.instructionDate = instructionDate;
	}

	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getAgreedFx() {
		if(nonNull(entityName)&&nonNull(currency)){
			return getAgreedExchangeRate(entityName+"_"+currency);
		}
		return null;
	}

}
