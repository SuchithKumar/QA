package com.bsc.qa.facets.bor_file_generator_stt.pojo;

import com.github.ffpojo.metadata.positional.annotation.PositionalField;
import com.github.ffpojo.metadata.positional.annotation.PositionalRecord;

@PositionalRecord
public class BORFileApp {
	
	private String claimId;
	private String fileName;
	private String vendorName;
	private String groupNumber;
	private String subgroupId;
	private String subscriberId;
	private String personNumber;
	private String claimNumber;
	private String claimVersionNumber;
	private String claimAmount;
	private String clientPrice;
	private String bscRevenueAmount;
	private String checkNumber;
	private String checkDate;
	private String serviceDate;
	private String payeeId;
	private String payeeName;
	private String planId;
	private String productId;
	private String productCategory;
	private String classId;
	private String productBusinessCategory;
	private String productValueCode;
	private String lineOfBusinessId;
	private String legalEntity;
	private String billedAmount;
	private String allowedAmount;
	private String deductibleAmount;
	private String coinsuranceAmount;
	private String copayAmount;
	private String diagnosisCode;
	private String diagnosisCodeType;
	private String procedureCode;
	private String hcpcs_id;
	private String claimTransactionType;
	
	@PositionalField(initialPosition = 1,finalPosition=12)
	public String getClaimId() {
		return claimId;
	}
	public void setClaimId(String claimId) {
		this.claimId = claimId;
	}
	@PositionalField(initialPosition = 13,finalPosition=47)
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@PositionalField(initialPosition = 48,finalPosition=51)
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	@PositionalField(initialPosition =52,finalPosition=59)
	public String getGroupNumber() {
		return groupNumber;
	}
	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}
	@PositionalField(initialPosition =60,finalPosition=65)
	public String getSubgroupId() {
		return subgroupId;
	}
	public void setSubgroupId(String subgroupId) {
		this.subgroupId = subgroupId;
	}
	@PositionalField(initialPosition =66,finalPosition=74)
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	@PositionalField(initialPosition = 75,finalPosition=76)
	public String getPersonNumber() {
		return personNumber;
	}
	public void setPersonNumber(String personNumber) {
		this.personNumber = personNumber;
	}
	@PositionalField(initialPosition = 77,finalPosition=88)
	public String getClaimNumber() {
		return claimNumber;
	}
	public void setClaimNumber(String claimNumber) {
		this.claimNumber = claimNumber;
	}
	@PositionalField(initialPosition = 89,finalPosition=90)
	public String getClaimVersionNumber() {
		return claimVersionNumber;
	}
	public void setClaimVersionNumber(String claimVersionNumber) {
		this.claimVersionNumber = claimVersionNumber;
	}
	@PositionalField(initialPosition = 91,finalPosition=100)
	public String getClaimAmount() {
		return claimAmount;
	}
	public void setClaimAmount(String claimAmount) {
		this.claimAmount = claimAmount;
	}
	@PositionalField(initialPosition = 101,finalPosition=110)
	public String getClientPrice() {
		return clientPrice;
	}
	public void setClientPrice(String clientPrice) {
		this.clientPrice = clientPrice;
	}
	@PositionalField(initialPosition = 111,finalPosition=120)
	public String getBscRevenueAmount() {
		return bscRevenueAmount;
	}
	public void setBscRevenueAmount(String bscRevenueAmount) {
		this.bscRevenueAmount = bscRevenueAmount;
	}
	@PositionalField(initialPosition = 121,finalPosition=127)
	public String getCheckNumber() {
		return checkNumber;
	}
	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}
	@PositionalField(initialPosition = 128,finalPosition=136)
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	@PositionalField(initialPosition = 137,finalPosition=145)
	public String getServiceDate() {
		return serviceDate;
	}
	public void setServiceDate(String serviceDate) {
		this.serviceDate = serviceDate;
	}
	@PositionalField(initialPosition = 146,finalPosition=154)
	public String getPayeeId() {
		return payeeId;
	}
	public void setPayeeId(String payeeId) {
		this.payeeId = payeeId;
	}
	@PositionalField(initialPosition = 155,finalPosition=204)
	public String getPayeeName() {
		return payeeName;
	}
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	@PositionalField(initialPosition = 205,finalPosition=212)
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	@PositionalField(initialPosition = 213,finalPosition=220)
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	@PositionalField(initialPosition = 221,finalPosition=221)
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	@PositionalField(initialPosition = 222,finalPosition=225)
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	@PositionalField(initialPosition = 226,finalPosition=229)
	public String getProductBusinessCategory() {
		return productBusinessCategory;
	}
	public void setProductBusinessCategory(String productBusinessCategory) {
		this.productBusinessCategory = productBusinessCategory;
	}
	@PositionalField(initialPosition = 230,finalPosition=233)
	public String getProductValueCode() {
		return productValueCode;
	}
	public void setProductValueCode(String productValueCode) {
		this.productValueCode = productValueCode;
	}
	@PositionalField(initialPosition = 234,finalPosition=237)
	public String getLineOfBusinessId() {
		return lineOfBusinessId;
	}
	public void setLineOfBusinessId(String lineOfBusinessId) {
		this.lineOfBusinessId = lineOfBusinessId;
	}
	@PositionalField(initialPosition = 238,finalPosition=241)
	public String getLegalEntity() {
		return legalEntity;
	}
	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}
	@PositionalField(initialPosition = 242,finalPosition=251)
	public String getBilledAmount() {
		return billedAmount;
	}
	public void setBilledAmount(String billedAmount) {
		this.billedAmount = billedAmount;
	}
	@PositionalField(initialPosition = 252,finalPosition=261)
	public String getAllowedAmount() {
		return allowedAmount;
	}
	public void setAllowedAmount(String allowedAmount) {
		this.allowedAmount = allowedAmount;
	}
	@PositionalField(initialPosition = 262,finalPosition=271)
	public String getDeductibleAmount() {
		return deductibleAmount;
	}
	public void setDeductibleAmount(String deductibleAmount) {
		this.deductibleAmount = deductibleAmount;
	}
	@PositionalField(initialPosition = 272,finalPosition=281)
	public String getCoinsuranceAmount() {
		return coinsuranceAmount;
	}
	public void setCoinsuranceAmount(String coinsuranceAmount) {
		this.coinsuranceAmount = coinsuranceAmount;
	}
	@PositionalField(initialPosition = 282,finalPosition=291)
	public String getCopayAmount() {
		return copayAmount;
	}
	public void setCopayAmount(String copayAmount) {
		this.copayAmount = copayAmount;
	}
	@PositionalField(initialPosition = 292,finalPosition=301)
	public String getDiagnosisCode() {
		return diagnosisCode;
	}
	public void setDiagnosisCode(String diagnosisCode) {
		this.diagnosisCode = diagnosisCode;
	}
	@PositionalField(initialPosition = 302,finalPosition=302)
	public String getDiagnosisCodeType() {
		return diagnosisCodeType;
	}
	public void setDiagnosisCodeType(String diagnosisCodeType) {
		this.diagnosisCodeType = diagnosisCodeType;
	}
	@PositionalField(initialPosition = 303,finalPosition=309)
	public String getProcedureCode() {
		return procedureCode;
	}
	public void setProcedureCode(String procedureCode) {
		this.procedureCode = procedureCode;
	}
	@PositionalField(initialPosition = 310,finalPosition=328)
	public String getHcpcs_id() {
		return hcpcs_id;
	}
	public void setHcpcs_id(String hcpcs_id) {
		this.hcpcs_id = hcpcs_id;
	}
	@PositionalField(initialPosition = 329,finalPosition=329)
	public String getClaimTransactionType() {
		return claimTransactionType;
	}
	public void setClaimTransactionType(String claimTransactionType) {
		this.claimTransactionType = claimTransactionType;
	}
	@Override
	public String toString() {
		return "BORFile [claimId=" + claimId + ", fileName=" + fileName
				+ ", vendorName=" + vendorName + ", groupNumber=" + groupNumber
				+ ", subgroupId=" + subgroupId + ", subscriberId="
				+ subscriberId + ", personNumber=" + personNumber
				+ ", claimNumber=" + claimNumber + ", claimVersionNumber="
				+ claimVersionNumber + ", claimAmount=" + claimAmount
				+ ", clientPrice=" + clientPrice + ", bscRevenueAmount="
				+ bscRevenueAmount + ", checkNumber=" + checkNumber
				+ ", checkDate=" + checkDate + ", serviceDate=" + serviceDate
				+ ", payeeId=" + payeeId + ", payeeName=" + payeeName
				+ ", planId=" + planId + ", productId=" + productId
				+ ", productCategory=" + productCategory + ", classId="
				+ classId + ", productBusinessCategory="
				+ productBusinessCategory + ", productValueCode="
				+ productValueCode + ", lineOfBusinessId=" + lineOfBusinessId
				+ ", legalEntity=" + legalEntity + ", billedAmount="
				+ billedAmount + ", allowedAmount=" + allowedAmount
				+ ", deductibleAmount=" + deductibleAmount
				+ ", coinsuranceAmount=" + coinsuranceAmount + ", copayAmount="
				+ copayAmount + ", diagnosisCode=" + diagnosisCode
				+ ", diagnosisCodeType=" + diagnosisCodeType
				+ ", procedureCode=" + procedureCode + ", hcpcs_id=" + hcpcs_id
				+ ", claimTransactionType=" + claimTransactionType + "]";
	}
	
	
}
