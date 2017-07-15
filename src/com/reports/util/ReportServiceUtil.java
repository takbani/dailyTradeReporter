package com.reports.util;

import static java.util.Arrays.asList;
import static java.util.Objects.nonNull;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.reports.model.TradingEntity;

public class ReportServiceUtil {


	public boolean isValidDayForSettlement(TradingEntity entity){
		String day=new SimpleDateFormat("EE").format(entity.getInstructionDate());
		List<String> validDaysForAEDAndSAR= new ArrayList<>(asList("Sun","Mon","Tue","Wed","Thu"));
		List<String> validDaysForNonAED= new ArrayList<>(asList("Mon","Tue","Wed","Thu","Fri"));
		if(nonNull(entity.getCurrency())){
			if(entity.getCurrency().equalsIgnoreCase("AED")||entity.getCurrency().equalsIgnoreCase("SAR")){
				return validDaysForAEDAndSAR.contains(day);
			}
			else {
				return validDaysForNonAED.contains(day);
			}
		}
		return false;
	}



	public DecimalFormat getDecimalFormat(){
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(0);
		df.setGroupingUsed(false);
		return df;

	}

}
