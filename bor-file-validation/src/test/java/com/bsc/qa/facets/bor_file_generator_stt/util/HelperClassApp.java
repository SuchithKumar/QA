package com.bsc.qa.facets.bor_file_generator_stt.util;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;

import com.bsc.qa.facets.afa.pojo.AmountFieldsApp;
import com.bsc.qa.facets.afa.pojo.BorAdjustmentDBApp;



public class HelperClassApp {

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
		String borFileName = "FACETS_PCT_AFAGL." + "#" + mm + dd + yy + "." + hh + mn + sc + ".txt";
		return borFileName;
	}
	
	public static String getTodaysDateFake(){
//		Date d = new Date();
//		Calendar cal = Calendar.getInstance(Locale.US);
//		cal.setTime(d);
//		TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
//		cal.setTimeZone(tz);
//		
//		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
		String send = "25-JUL-19";
//		System.out.println(dateFormat.format(d).toUpperCase());
		return send;
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
	public static String getLastYearsDate(){
		Date d = new Date();
		Calendar cal = Calendar.getInstance(Locale.US);
		cal.setTime(d);
	
		TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
		cal.setTimeZone(tz);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yy");
		String yearString = String.valueOf((Integer.parseInt(dateFormat1.format(d))-1));
//		System.out.println(dateFormat.format(d).toUpperCase());
		return dateFormat.format(d).toUpperCase().concat(yearString);
	}
	
	public static AmountFieldsApp getAmountFields(BigDecimal billedAmount){
		AmountFieldsApp amountFields = new AmountFieldsApp();
		//ingredientCost
		//BigDecimal billedAmount =(new BigDecimal(ThreadLocalRandom.current().nextInt(400, 999 + 1))).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal ingredientCost = billedAmount;//.divide(new BigDecimal(2)).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal allowedAmount = billedAmount.multiply((new BigDecimal(0.6))).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal deductible = billedAmount.multiply((new BigDecimal(0.2))).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal copay = billedAmount.multiply((new BigDecimal(0.1))).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal coinsurance = billedAmount.multiply((new BigDecimal(0.1))).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal claimAmount = ingredientCost.subtract(deductible).subtract(copay).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal clientPrice = billedAmount;
		BigDecimal bscRevenueAmount = clientPrice.subtract(claimAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
		
		amountFields.setClaimAmount(String.valueOf(claimAmount));
		amountFields.setBilledAmount(String.valueOf(billedAmount));
		amountFields.setDeductible(String.valueOf(deductible));
		amountFields.setCopay(String.valueOf(copay));
		amountFields.setCoInsurance(String.valueOf(coinsurance));
		amountFields.setClientPrice(String.valueOf(clientPrice));
		amountFields.setBscRevenueAmount(String.valueOf(bscRevenueAmount));
		amountFields.setAllowedAmount(String.valueOf(allowedAmount));
		
//		System.out.println(amountFields);
		return amountFields;
	}

	public static AmountFieldsApp getAdjustmentAmountFields(BigDecimal billedAmount){
		AmountFieldsApp amountFields = new AmountFieldsApp();
		
		//BigDecimal billedAmount =(new BigDecimal(ThreadLocalRandom.current().nextInt(400, 999 + 1))).setScale(2, BigDecimal.ROUND_HALF_UP);
		//BigDecimal ingredientCost = billedAmount.divide(new BigDecimal(2)).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal allowedAmount = billedAmount.multiply((new BigDecimal(0.6))).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal deductible = billedAmount.multiply((new BigDecimal(0.2))).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal copay = billedAmount.multiply((new BigDecimal(0.1))).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal coinsurance = billedAmount.multiply((new BigDecimal(0.1))).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal claimAmount = billedAmount.divide(new BigDecimal(2)).setScale(2 , BigDecimal.ROUND_HALF_UP);
		BigDecimal clientPrice = billedAmount;
		BigDecimal bscRevenueAmount = claimAmount;
		
		amountFields.setClaimAmount(String.valueOf(claimAmount));
		amountFields.setBilledAmount(String.valueOf(billedAmount));
		amountFields.setDeductible(String.valueOf(deductible));
		amountFields.setCopay(String.valueOf(copay));
		amountFields.setCoInsurance(String.valueOf(coinsurance));
		amountFields.setClientPrice(String.valueOf(clientPrice));
		amountFields.setBscRevenueAmount(String.valueOf(bscRevenueAmount));
		amountFields.setAllowedAmount(String.valueOf(allowedAmount));
		
//		System.out.println(amountFields);
		return amountFields;
	}
	
	public static AmountFieldsApp getAdjustmentAmountFieldsScenario3(BigDecimal billedAmount,BorAdjustmentDBApp adjustmentRecord){
		AmountFieldsApp amountFields = new AmountFieldsApp();
		
		//BigDecimal billedAmount =(new BigDecimal(ThreadLocalRandom.current().nextInt(400, 999 + 1))).setScale(2, BigDecimal.ROUND_HALF_UP);
		//BigDecimal ingredientCost = billedAmount.divide(new BigDecimal(2)).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal allowedAmount = billedAmount.multiply((new BigDecimal(0.6))).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal deductible = billedAmount.multiply((new BigDecimal(0.2))).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal copay = billedAmount.multiply((new BigDecimal(0.1))).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal coinsurance = billedAmount.multiply((new BigDecimal(0.1))).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal claimAmount = billedAmount.divide(new BigDecimal(2)).setScale(2 , BigDecimal.ROUND_HALF_UP);
		BigDecimal clientPrice = billedAmount;
		BigDecimal bscRevenueAmount = claimAmount;
		
		if(adjustmentRecord.getCLM_AMT().compareTo(claimAmount)==1){
//			System.out.println(adjustmentRecord.getCLM_AMT()+" adjustmentRecord.getCLM_AMT()");
		amountFields.setClaimAmount(String.valueOf(claimAmount));
		}else{
			amountFields.setClaimAmount(String.valueOf(adjustmentRecord.getCLM_AMT()));
		}
		if(adjustmentRecord.getBIL_AMT().compareTo(billedAmount)==1){
//			System.out.println(adjustmentRecord.getBIL_AMT()+" adjustmentRecord.getBIL_AMT()");
		amountFields.setBilledAmount(String.valueOf(billedAmount));
		}else{
			amountFields.setBilledAmount(String.valueOf(adjustmentRecord.getBIL_AMT()));
		}
		if(adjustmentRecord.getDED_AMT().compareTo(deductible)==1){
//			System.out.println(adjustmentRecord.getDED_AMT()+" adjustmentRecord.getDED_AMT()");
		amountFields.setDeductible(String.valueOf(deductible));
		}else{
			amountFields.setDeductible(String.valueOf(adjustmentRecord.getDED_AMT()));
		}
		if(adjustmentRecord.getCOPAY_AMT().compareTo(copay)==1){
//			System.out.println(adjustmentRecord.getCOPAY_AMT()+" adjustmentRecord.getCOPAY_AMT()");
		amountFields.setCopay(String.valueOf(copay));
		}else{
			amountFields.setCopay(String.valueOf(adjustmentRecord.getCOPAY_AMT()));
		}
		if(adjustmentRecord.getCOINS_AMT().compareTo(coinsurance)==1){
//			System.out.println(adjustmentRecord.getCOINS_AMT()+" adjustmentRecord.getCOINS_AMT()");
		amountFields.setCoInsurance(String.valueOf(coinsurance));
		}else{
			amountFields.setCoInsurance(String.valueOf(adjustmentRecord.getCOINS_AMT()));
		}
		if(adjustmentRecord.getCLI_PRC_AMT().compareTo(clientPrice)==1){
//			System.out.println(adjustmentRecord.getCLI_PRC_AMT()+" adjustmentRecord.getCLI_PRC_AMT()");
		amountFields.setClientPrice(String.valueOf(clientPrice));
		}else{
			amountFields.setClientPrice(String.valueOf(adjustmentRecord.getCLI_PRC_AMT()));
		}
		if(adjustmentRecord.getBSC_RVNU_AMT().compareTo(bscRevenueAmount)==1){
//			System.out.println(adjustmentRecord.getBSC_RVNU_AMT()+" adjustmentRecord.getBSC_RVNU_AMT()");
			amountFields.setBscRevenueAmount(String.valueOf(bscRevenueAmount));
		}else{
			amountFields.setBscRevenueAmount(String.valueOf(adjustmentRecord.getBSC_RVNU_AMT()));
		}
		if(adjustmentRecord.getALLOW_AMT().compareTo(allowedAmount)==1){
//			System.out.println(adjustmentRecord.getALLOW_AMT()+" adjustmentRecord.getALLOW_AMT()");
		amountFields.setAllowedAmount(String.valueOf(allowedAmount));
		}else{
			amountFields.setAllowedAmount(String.valueOf(adjustmentRecord.getALLOW_AMT()));
		}
		
//		System.out.println(amountFields);
		return amountFields;
	}

	public static int getDecimalForAmountFields(){
		int randomDecimals = ThreadLocalRandom.current().nextInt(10, 99 + 1);
			return randomDecimals;
		
		}
	
}
