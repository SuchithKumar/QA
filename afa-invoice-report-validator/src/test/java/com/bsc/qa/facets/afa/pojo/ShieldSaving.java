package com.bsc.qa.facets.afa.pojo;

public class ShieldSaving {

	/*
	 * CLAIMPAYMENTPERIOD	GROUPNAME	GROUPBILLINGID	CLAIMSCYCLE	BILDUEDATE	
	 * INVOICENO	BILLINGCATEGORY	PROVIDERCHARGEDAMOUNT	SAVINGS	DISALLOWED	
	 * ALLOWEDAMOUNT	COSTCONTAINMENT	TOTAL
	 */
	
	private String claimPaymentPeriod;
	private String groupName;
	private String groupBillingId;
	private String claimsCycle;
	private String billDueDate;
	private String invoiceNo;
	private String billingCategory;
	private String providerChargedAmount;
	private String savings;
	private String disallowed;
	private String allowedAmount;
	private String costContainment;
	private String total;
	public String getClaimPaymentPeriod() {
		return claimPaymentPeriod;
	}
	public void setClaimPaymentPeriod(String claimPaymentPeriod) {
		this.claimPaymentPeriod = claimPaymentPeriod;
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
	public String getProviderChargedAmount() {
		return providerChargedAmount;
	}
	public void setProviderChargedAmount(String providerChargedAmount) {
		this.providerChargedAmount = providerChargedAmount;
	}
	public String getSavings() {
		return savings;
	}
	public void setSavings(String savings) {
		this.savings = savings;
	}
	public String getDisallowed() {
		return disallowed;
	}
	public void setDisallowed(String disallowed) {
		this.disallowed = disallowed;
	}
	public String getAllowedAmount() {
		return allowedAmount;
	}
	public void setAllowedAmount(String allowedAmount) {
		this.allowedAmount = allowedAmount;
	}
	public String getCostContainment() {
		return costContainment;
	}
	public void setCostContainment(String costContainment) {
		this.costContainment = costContainment;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	@Override
	public String toString() {
		return "ShieldSaving [claimPaymentPeriod=" + claimPaymentPeriod
				+ ", groupName=" + groupName + ", groupBillingId="
				+ groupBillingId + ", claimsCycle=" + claimsCycle
				+ ", billDueDate=" + billDueDate + ", invoiceNo=" + invoiceNo
				+ ", billingCategory=" + billingCategory
				+ ", providerChargedAmount=" + providerChargedAmount
				+ ", savings=" + savings + ", disallowed=" + disallowed
				+ ", allowedAmount=" + allowedAmount + ", costContainment="
				+ costContainment + ", total=" + total + "]";
	}
	
	 @Override
     public boolean equals(Object o) {
         if (this == o) {
             return true;
         }
         if (o == null || getClass() != o.getClass()) {
             return false;
         }
         ShieldSaving shieldSaving = (ShieldSaving)o;
         return this.claimPaymentPeriod.equals(shieldSaving.claimPaymentPeriod) &&
        		this.groupName.equals(shieldSaving.groupName) &&
        		this.groupBillingId.equals(shieldSaving.groupBillingId) &&
        		this.claimsCycle.equals(shieldSaving.claimsCycle) &&
        		this.billDueDate.equals(shieldSaving.billDueDate) &&
        		this.invoiceNo.equals(shieldSaving.invoiceNo) &&
        		this.billingCategory.equals(shieldSaving.billingCategory) &&
        		this.providerChargedAmount.equals(shieldSaving.providerChargedAmount) &&
        		this.savings.equals(shieldSaving.savings) &&
        		this.disallowed.equals(shieldSaving.disallowed) &&
        		this.allowedAmount.equals(shieldSaving.allowedAmount) &&
        		this.costContainment.equals(shieldSaving.costContainment) &&
        		this.total.equals(shieldSaving.total) ;
	 }
	
}
