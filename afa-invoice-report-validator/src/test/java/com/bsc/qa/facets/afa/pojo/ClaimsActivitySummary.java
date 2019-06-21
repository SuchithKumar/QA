package com.bsc.qa.facets.afa.pojo;

public class ClaimsActivitySummary {
	
	/*
	 * CLAIMPAYMENTPERIOD	INVOICEDATE	GROUPNAME	GROUPBILLINGID	CLAIMSCYCLE	
	 * BILDUEDATE	INVOICENO	BILLINGCATEGORY	MEDICAL	COSTCONTAINMENT	INTEREST	
	 * DENTAL	PHARMACY	BLUECARDACCESSFEES	STOPLOSSADVANCEDFUNDING	
	 * HEALTHREIMBURSEMENTACCOUNT SUBTOTALCLAIMSACTIVITY	TOTAL
	 */

	private String claimPaymentPeriod;
	private String invoiceDate;
	private String groupName;
	private String groupBillingId;
	private String claimsCycle;
	private String billDueDate;
	private String  invoiceNo;
	private String billingCategory;
	private String medical;
	private String costContainment;
	private String interest;
	private String dental;
	private String pharmacy;
	private String blueCardAccessFees;
	private String stopLossAdvancedFunding;
	private String healthReimbursementAccount;
	private String subTotalClaimsActivity;
	private String total;
	
	public String getClaimPaymentPeriod() {
		return claimPaymentPeriod;
	}
	public void setClaimPaymentPeriod(String claimPaymentPeriod) {
		this.claimPaymentPeriod = claimPaymentPeriod;
	}
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupBillingId() {
		return groupBillingId;
	}
	public void setGroupBillingId(String groupBillingId) {
		this.groupBillingId = groupBillingId;
	}
	public String getClaimsCycle() {
		return claimsCycle;
	}
	public void setClaimsCycle(String claimsCycle) {
		this.claimsCycle = claimsCycle;
	}
	public String getBillDueDate() {
		return billDueDate;
	}
	public void setBillDueDate(String billDueDate) {
		this.billDueDate = billDueDate;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getBillingCategory() {
		return billingCategory;
	}
	public void setBillingCategory(String billingCategory) {
		this.billingCategory = billingCategory;
	}
	public String getMedical() {
		return medical;
	}
	public void setMedical(String medical) {
		this.medical = medical;
	}
	public String getCostContainment() {
		return costContainment;
	}
	public void setCostContainment(String costContainment) {
		this.costContainment = costContainment;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getDental() {
		return dental;
	}
	public void setDental(String dental) {
		this.dental = dental;
	}
	public String getPharmacy() {
		return pharmacy;
	}
	public void setPharmacy(String pharmacy) {
		this.pharmacy = pharmacy;
	}
	public String getBlueCardAccessFees() {
		return blueCardAccessFees;
	}
	public void setBlueCardAccessFees(String blueCardAccessFees) {
		this.blueCardAccessFees = blueCardAccessFees;
	}
	public String getStopLossAdvancedFunding() {
		return stopLossAdvancedFunding;
	}
	public void setStopLossAdvancedFunding(String stopLossAdvancedFunding) {
		this.stopLossAdvancedFunding = stopLossAdvancedFunding;
	}
	public String getHealthReimbursementAccount() {
		return healthReimbursementAccount;
	}
	public void setHealthReimbursementAccount(String healthReimbursementAccount) {
		this.healthReimbursementAccount = healthReimbursementAccount;
	}
	public String getSubTotalClaimsActivity() {
		return subTotalClaimsActivity;
	}
	public void setSubTotalClaimsActivity(String subTotalClaimsActivity) {
		this.subTotalClaimsActivity = subTotalClaimsActivity;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	
	@Override
	public String toString() {
		return "ClaimsActivitySummary [claimPaymentPeriod="
				+ claimPaymentPeriod + ", invoiceDate=" + invoiceDate
				+ ", groupName=" + groupName + ", groupBillingId="
				+ groupBillingId + ", claimsCycle=" + claimsCycle
				+ ", billDueDate=" + billDueDate + ", invoiceNo=" + invoiceNo
				+ ", billingCategory=" + billingCategory + ", medical="
				+ medical + ", costContainment=" + costContainment
				+ ", interest=" + interest + ", dental=" + dental
				+ ", pharmacy=" + pharmacy + ", blueCardAccessFees="
				+ blueCardAccessFees + ", stopLossAdvancedFunding="
				+ stopLossAdvancedFunding + ", healthReimbursementAccount="
				+ healthReimbursementAccount + ", subTotalClaimsActivity="
				+ subTotalClaimsActivity + ", total=" + total + "]";
	}
	
	
}
