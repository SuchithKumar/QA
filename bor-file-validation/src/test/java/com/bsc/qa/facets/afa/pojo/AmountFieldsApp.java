package com.bsc.qa.facets.afa.pojo;

public class AmountFieldsApp {

	String claimAmount;
	String clientPrice;
	String bscRevenueAmount;
	String billedAmount;
	String allowedAmount;
	String copay;
	String coInsurance;
	String deductible;
	public String getClaimAmount() {
		return claimAmount;
	}
	public void setClaimAmount(String claimAmount) {
		this.claimAmount = claimAmount;
	}
	public String getClientPrice() {
		return clientPrice;
	}
	public void setClientPrice(String clientPrice) {
		this.clientPrice = clientPrice;
	}
	public String getBscRevenueAmount() {
		return bscRevenueAmount;
	}
	public void setBscRevenueAmount(String bscRevenueAmount) {
		this.bscRevenueAmount = bscRevenueAmount;
	}
	public String getBilledAmount() {
		return billedAmount;
	}
	public void setBilledAmount(String billedAmount) {
		this.billedAmount = billedAmount;
	}
	public String getAllowedAmount() {
		return allowedAmount;
	}
	public void setAllowedAmount(String allowedAmount) {
		this.allowedAmount = allowedAmount;
	}
	public String getCopay() {
		return copay;
	}
	public void setCopay(String copay) {
		this.copay = copay;
	}
	public String getCoInsurance() {
		return coInsurance;
	}
	public void setCoInsurance(String coInsurance) {
		this.coInsurance = coInsurance;
	}
	public String getDeductible() {
		return deductible;
	}
	public void setDeductible(String deductible) {
		this.deductible = deductible;
	}
	@Override
	public String toString() {
		return "AmountFields [claimAmount=" + claimAmount + ", clientPrice="
				+ clientPrice + ", bscRevenueAmount=" + bscRevenueAmount
				+ ", billedAmount=" + billedAmount + ", allowedAmount="
				+ allowedAmount + ", copay=" + copay + ", coInsurance="
				+ coInsurance + ", deductible=" + deductible + "]";
	}
	
	
	
}
