package com.bsc.qa.facets.afa.pojo;

import java.util.Date;

public class FrontPage {
	
	private String groupName;
	private String groupAddress;
	private String attention;
	private String groupBillingId;
	private String fundingPeriod;
	private String billDueDate;
	private String invoiceNo;
	private String invoiceDate;
	private String currentPeriodClaims;
	private String balanceForward;
	private String totalDueForClaimsReimbursement;
	private String bscAccountantName;
	private String phone;
	private String fax;
	private String email;
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupAddress() {
		return groupAddress;
	}
	public void setGroupAddress(String groupAddress) {
		this.groupAddress = groupAddress;
	}
	public String getAttention() {
		return attention;
	}
	public void setAttention(String attention) {
		this.attention = attention;
	}
	public String getGroupBillingId() {
		return groupBillingId;
	}
	public void setGroupBillingId(String groupBillingId) {
		this.groupBillingId = groupBillingId;
	}
	public String getFundingPeriod() {
		return fundingPeriod;
	}
	public void setFundingPeriod(String fundingPeriod) {
		this.fundingPeriod = fundingPeriod;
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
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getCurrentPeriodClaims() {
		return currentPeriodClaims;
	}
	public void setCurrentPeriodClaims(String currentPeriodClaims) {
		this.currentPeriodClaims = currentPeriodClaims;
	}
	public String getBalanceForward() {
		return balanceForward;
	}
	public void setBalanceForward(String balanceForward) {
		this.balanceForward = balanceForward;
	}
	public String getTotalDueForClaimsReimbursement() {
		return totalDueForClaimsReimbursement;
	}
	public void setTotalDueForClaimsReimbursement(
			String totalDueForClaimsReimbursement) {
		this.totalDueForClaimsReimbursement = totalDueForClaimsReimbursement;
	}
	public String getBscAccountantName() {
		return bscAccountantName;
	}
	public void setBscAccountantName(String bscAccountantName) {
		this.bscAccountantName = bscAccountantName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "FrontPage [groupName=" + groupName + ", groupAddress="
				+ groupAddress + ", attention=" + attention
				+ ", groupBillingId=" + groupBillingId + ", fundingPeriod="
				+ fundingPeriod + ", billDueDate=" + billDueDate
				+ ", invoiceNo=" + invoiceNo + ", invoiceDate=" + invoiceDate
				+ ", currentPeriodClaims=" + currentPeriodClaims
				+ ", balanceForward=" + balanceForward
				+ ", totalDueForClaimsReimbursement="
				+ totalDueForClaimsReimbursement + ", bscAccountantName="
				+ bscAccountantName + ", phone=" + phone + ", fax=" + fax
				+ ", email=" + email + "]";
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FrontPage frontPage = (FrontPage)o;
        return this.groupName.equals(frontPage.groupName) &&
       		this.groupAddress.equals(frontPage.groupAddress) &&
       		this.groupBillingId.equals(frontPage.groupBillingId) &&
       		this.attention.equals(frontPage.attention) &&
       		this.fundingPeriod.equals(frontPage.fundingPeriod) &&
       		this.billDueDate.equals(frontPage.billDueDate) &&
       		this.invoiceNo.equals(frontPage.invoiceNo) &&
       		this.invoiceDate.equals(frontPage.invoiceDate) &&
       		this.currentPeriodClaims.equals(frontPage.currentPeriodClaims) &&
       		this.balanceForward.equals(frontPage.balanceForward) &&
       		this.totalDueForClaimsReimbursement.equals(frontPage.totalDueForClaimsReimbursement) &&
       		this.bscAccountantName.equals(frontPage.bscAccountantName) &&
       		this.phone.equals(frontPage.phone) && 
       		this.fax.equals(frontPage.fax) && 
       		this.email.equals(frontPage.email) ;
	 }
	
}
