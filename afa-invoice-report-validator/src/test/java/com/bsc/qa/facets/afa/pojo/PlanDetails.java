package com.bsc.qa.facets.afa.pojo;

import java.math.BigDecimal;

public class PlanDetails {
/*
 * GROUPNAME	AFAID	CLAIMSCYCLE	BILDUEDATE	INVOICENO	GROUPIDNAME	
 * PLN	CBC	GRPID	COVERAGE	PLANDETAILSTOTALS	MEDICAL	COSTCONTAINMENT	
 * INTEREST	DENTAL	PHARMACY	STOPLOSS	BLUECARD	HRA	TOTALPAID	
 * SORTORDER1	SORTORDER2
 */
	private String groupName;
	private String afaId;
	private String claimsCycle;
	private String billDueDate;
	private String invoiceNo;
	private String groupIdName;
	private String pln;
	private String cbc;
	private String grpId;
	private String coverage;
	private String planDetailsTotals;
	private String medical;
	private String costContainment;
	private String interest;
	private String dental;
	private String pharmacy;
	private String stoploss;
	private String bluecard;
	private String hra;
	private String totalPaid;
	private BigDecimal sortOrder1;
	private BigDecimal sortOrder2;
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getAfaId() {
		return afaId;
	}
	public void setAfaId(String afaId) {
		this.afaId = afaId;
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
	public String getGroupIdName() {
		return groupIdName;
	}
	public void setGroupIdName(String groupIdName) {
		this.groupIdName = groupIdName;
	}
	public String getPln() {
		return pln;
	}
	public void setPln(String pln) {
		this.pln = pln;
	}
	public String getCbc() {
		return cbc;
	}
	public void setCbc(String cbc) {
		this.cbc = cbc;
	}
	public String getGrpId() {
		return grpId;
	}
	public void setGrpId(String grpId) {
		this.grpId = grpId;
	}
	public String getCoverage() {
		return coverage;
	}
	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}
	public String getPlanDetailsTotals() {
		return planDetailsTotals;
	}
	public void setPlanDetailsTotals(String planDetailsTotals) {
		this.planDetailsTotals = planDetailsTotals;
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
	public String getStoploss() {
		return stoploss;
	}
	public void setStoploss(String stoploss) {
		this.stoploss = stoploss;
	}
	public String getBluecard() {
		return bluecard;
	}
	public void setBluecard(String bluecard) {
		this.bluecard = bluecard;
	}
	public String getHra() {
		return hra;
	}
	public void setHra(String hra) {
		this.hra = hra;
	}
	public String getTotalPaid() {
		return totalPaid;
	}
	public void setTotalPaid(String totalPaid) {
		this.totalPaid = totalPaid;
	}
	public BigDecimal getSortOrder1() {
		return sortOrder1;
	}
	public void setSortOrder1(BigDecimal sortOrder1) {
		this.sortOrder1 = sortOrder1;
	}
	public BigDecimal getSortOrder2() {
		return sortOrder2;
	}
	public void setSortOrder2(BigDecimal sortOrder2) {
		this.sortOrder2 = sortOrder2;
	}
	@Override
	public String toString() {
		return "PlanDetails [groupName=" + groupName + ", afaId=" + afaId
				+ ", claimsCycle=" + claimsCycle + ", billDueDate="
				+ billDueDate + ", invoiceNo=" + invoiceNo + ", groupIdName="
				+ groupIdName + ", pln=" + pln + ", cbc=" + cbc + ", grpId="
				+ grpId + ", coverage=" + coverage + ", planDetailsTotals="
				+ planDetailsTotals + ", medical=" + medical
				+ ", costContainment=" + costContainment + ", interest="
				+ interest + ", dental=" + dental + ", pharmacy=" + pharmacy
				+ ", stoploss=" + stoploss + ", bluecard=" + bluecard
				+ ", hra=" + hra + ", totalPaid=" + totalPaid + ", sortOrder1="
				+ sortOrder1 + ", sortOrder2=" + sortOrder2 + "]";
	}
	
	
	
	
}
