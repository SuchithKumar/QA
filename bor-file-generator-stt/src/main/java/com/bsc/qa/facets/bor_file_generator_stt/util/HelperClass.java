package com.bsc.qa.facets.bor_file_generator_stt.util;

import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;

import com.bsc.qa.facets.bor_file_generator_stt.pojo.AmountFields;

public class HelperClass {

	private static String dd;
	private static String mm;
	private static String hh;
	private static String mn;
	private static String sc;

	public static String getBorFileName(){
		Date d = new Date();
		Calendar cal = Calendar.getInstance(Locale.US);
		cal.setTime(d);
		TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
		cal.setTimeZone(tz);

		int dd_i = cal.get(Calendar.DATE);
		if (dd_i < 10) {
			dd = "0" + dd_i;
		} else {
			dd = "" + dd_i;
		}

		int mm_i = cal.get(Calendar.MONTH);
		mm_i += 1;
		if (mm_i < 10) {
			mm = "0" + mm_i;
		} else {
			mm = "" + mm_i;
		}

		int clm_id = 0;
		Random r = new Random();
		int x = r.nextInt(999999);
		if (x < 100000) {
			x = x + 100000;
		}
		clm_id = x;
		int yy = cal.get(Calendar.YEAR);
		int hh_i = cal.get(Calendar.HOUR);
		if (hh_i < 10) {
			hh = "0" + hh_i;
		} else {
			hh = "" + hh_i;
		}
		
		int mn_i = cal.get(Calendar.MINUTE);
		if(mn_i < 10){
			mn = "0" + mn_i;
		}else{
			mn = "" + mn_i;
		}
		
		int sc_i = cal.get(Calendar.SECOND);
		if(sc_i < 10){
			sc = "0" + sc_i;
		}else{
			sc = "" + sc_i;
		}
		
		yy = yy % 100;
		File dir=new File("Output files");
		if(!dir.exists())
		dir.mkdir();
		String borFileName = "FACETS_PCT_AFAGL." + "#" + dd + mm + yy + "." + hh + mn + sc + ".txt";
		return borFileName;
	}
	
	public static String getTodaysDate(){
		Date d = new Date();
		Calendar cal = Calendar.getInstance(Locale.US);
		cal.setTime(d);
		TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
		cal.setTimeZone(tz);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
//		System.out.println(dateFormat.format(d).toUpperCase());
		return dateFormat.format(d).toUpperCase();
	}
	
	public static AmountFields getAmountFields(){
		AmountFields amountFields = new AmountFields();
		
		BigDecimal billedAmount =(new BigDecimal(ThreadLocalRandom.current().nextInt(400, 999 + 1))).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal ingredientCost = billedAmount.divide(new BigDecimal(2)).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal deductible = (new BigDecimal(ThreadLocalRandom.current().nextInt(10, 29 + 1))).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal copay = (new BigDecimal(ThreadLocalRandom.current().nextInt(10, 29 + 1))).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal coinsurance = (new BigDecimal(ThreadLocalRandom.current().nextInt(10, 29 + 1))).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal claimAmount = ingredientCost.subtract(deductible).subtract(copay).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal clientPrice = billedAmount;
		BigDecimal bscRevenueAmount = clientPrice.subtract(claimAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal allowedAmount = billedAmount.multiply((new BigDecimal(0.6))).setScale(2, BigDecimal.ROUND_HALF_UP);
		
		amountFields.setBilledAmount(String.valueOf(billedAmount));
		amountFields.setDeductible(String.valueOf(deductible));
		amountFields.setCopay(String.valueOf(copay));
		amountFields.setCoInsurance(String.valueOf(coinsurance));
		if(claimAmount.compareTo(new BigDecimal(100)) == -1){
			amountFields.setClaimAmount(" "+String.valueOf(claimAmount));
		}else{
		amountFields.setClaimAmount(String.valueOf(claimAmount));
		}
		amountFields.setClientPrice(String.valueOf(clientPrice));
		amountFields.setBscRevenueAmount(String.valueOf(bscRevenueAmount));
		amountFields.setAllowedAmount(String.valueOf(allowedAmount));
		
//		System.out.println(amountFields);
		return amountFields;
	}

	public static int getDecimalForAmountFields(){
		int randomDecimals = ThreadLocalRandom.current().nextInt(10, 99 + 1);
			return randomDecimals;
		
		}
	
}
