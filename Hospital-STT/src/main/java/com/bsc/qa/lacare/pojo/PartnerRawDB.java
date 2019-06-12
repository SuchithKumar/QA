package com.bsc.qa.lacare.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PARTNER_RAW.LAC_HOSPITAL")
public class PartnerRawDB {

	BigDecimal PIMS_PROV_ID;
	BigDecimal PIMS_PROV_TIN_LOC_ID;
	BigDecimal PIMS_NET_ID;
	String SITE_ID;
	@Id
	BigDecimal LAC_BATCH_ID;
	Date DATE_CREATED;
	
	public BigDecimal getPIMS_PROV_ID() {
		return PIMS_PROV_ID;
	}
	public void setPIMS_PROV_ID(BigDecimal pIMS_PROV_ID) {
		PIMS_PROV_ID = pIMS_PROV_ID;
	}
	public BigDecimal getPIMS_PROV_TIN_LOC_ID() {
		return PIMS_PROV_TIN_LOC_ID;
	}
	public void setPIMS_PROV_TIN_LOC_ID(BigDecimal pIMS_PROV_TIN_LOC_ID) {
		PIMS_PROV_TIN_LOC_ID = pIMS_PROV_TIN_LOC_ID;
	}
	public BigDecimal getPIMS_NET_ID() {
		return PIMS_NET_ID;
	}
	public void setPIMS_NET_ID(BigDecimal pIMS_NET_ID) {
		PIMS_NET_ID = pIMS_NET_ID;
	}
	public BigDecimal getLAC_BATCH_ID() {
		return LAC_BATCH_ID;
	}
	public void setLAC_BATCH_ID(BigDecimal lAC_BATCH_ID) {
		LAC_BATCH_ID = lAC_BATCH_ID;
	}
	public Date getDATE_CREATED() {
		return DATE_CREATED;
	}
	public void setDATE_CREATED(Date dATE_CREATED) {
		DATE_CREATED = dATE_CREATED;
	}
	public String getSITE_ID() {
		return SITE_ID;
	}
	public void setSITE_ID(String sITE_ID) {
		SITE_ID = sITE_ID;
	}
	
	
}
