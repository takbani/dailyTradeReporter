package com.reports.dao;

import static java.math.BigDecimal.valueOf;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class AgreedExchangeRateDeriver {

	protected static Map<String,BigDecimal> agreedExchangeRate= new HashMap<>();

	private AgreedExchangeRateDeriver(){
	}

	private static void populateExchangeRatesForEntities(){
		agreedExchangeRate.put("foo_INR",valueOf(0.016));
		agreedExchangeRate.put("foo_SGP",valueOf(0.50));
		agreedExchangeRate.put("foo_AED",valueOf(0.23));
		agreedExchangeRate.put("foo_GBP",valueOf(1.31));

		agreedExchangeRate.put("bar_INR",valueOf(0.016));
		agreedExchangeRate.put("bar_SGP",valueOf(0.52));
		agreedExchangeRate.put("bar_AED",valueOf(0.22));
		agreedExchangeRate.put("bar_GBP",valueOf(1.33));

		agreedExchangeRate.put("inrCompany_INR",valueOf(0.016));
		agreedExchangeRate.put("inrCompany_SGP",valueOf(0.52));
		agreedExchangeRate.put("inrCompany_AED",valueOf(0.22));
		agreedExchangeRate.put("inrCompany_GBP",valueOf(1.33));

		agreedExchangeRate.put("gbpCompany_INR",valueOf(0.016));
		agreedExchangeRate.put("gbpCompany_SGP",valueOf(0.52));
		agreedExchangeRate.put("gbpCompany_AED",valueOf(0.22));
		agreedExchangeRate.put("gbpCompany_GBP",valueOf(1.33));

	}


	public static BigDecimal getAgreedExchangeRate(String entityAndCurrency){
		populateExchangeRatesForEntities();
		return agreedExchangeRate.get(entityAndCurrency);

	}


}
