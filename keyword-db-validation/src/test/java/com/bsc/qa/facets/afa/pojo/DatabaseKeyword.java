package com.bsc.qa.facets.afa.pojo;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="FC_CMC_BLXP_EXT_PYMT")
public class DatabaseKeyword {

	
	@Id
	private String BLXP_BATCH_ID;
	private int BLXP_SEQ_NO;
	private String BLXP_CLCL_ID;
	private long BLXP_CL_LINE_NO;
	private long MEME_CK;
	private long SBSB_CK;
	private long GRGR_CK;
	private long SGSG_CK;
	private String CSPD_CAT;
	private String CSPI_ID;
	private String PDPD_ID;
	private String IDCD_ID;
	private String IPCD_ID;
	private String BLXP_ACCT_CAT;
	private String BLXP_EXP_CAT;
	private Date BLXP_FROM_DT;
	private Date BLXP_TO_DT;
	private Date BLXP_PAID_DT;
	private double BLXP_PAID_AMT;
	private String LOBD_ID;
	private Date BLXP_CREATE_DTM;
	private long BLXP_STS;
	private double BLXP_HSA_PAID_AMT;
	private int BLXP_LOCK_TOKEN;
	private Date ATXR_SOURCE_ID;
	private String IDCD_TYPE_NVL;
	private String CSCS_ID_NVL;
	public String getBLXP_BATCH_ID() {
		return BLXP_BATCH_ID;
	}
	public void setBLXP_BATCH_ID(String bLXP_BATCH_ID) {
		BLXP_BATCH_ID = bLXP_BATCH_ID;
	}
	public int getBLXP_SEQ_NO() {
		return BLXP_SEQ_NO;
	}
	public void setBLXP_SEQ_NO(int bLXP_SEQ_NO) {
		BLXP_SEQ_NO = bLXP_SEQ_NO;
	}
	public String getBLXP_CLCL_ID() {
		return BLXP_CLCL_ID;
	}
	public void setBLXP_CLCL_ID(String bLXP_CLCL_ID) {
		BLXP_CLCL_ID = bLXP_CLCL_ID;
	}
	public long getBLXP_CL_LINE_NO() {
		return BLXP_CL_LINE_NO;
	}
	public void setBLXP_CL_LINE_NO(long bLXP_CL_LINE_NO) {
		BLXP_CL_LINE_NO = bLXP_CL_LINE_NO;
	}
	public long getMEME_CK() {
		return MEME_CK;
	}
	public void setMEME_CK(long mEME_CK) {
		MEME_CK = mEME_CK;
	}
	public long getSBSB_CK() {
		return SBSB_CK;
	}
	public void setSBSB_CK(long sBSB_CK) {
		SBSB_CK = sBSB_CK;
	}
	public long getGRGR_CK() {
		return GRGR_CK;
	}
	public void setGRGR_CK(long gRGR_CK) {
		GRGR_CK = gRGR_CK;
	}
	public long getSGSG_CK() {
		return SGSG_CK;
	}
	public void setSGSG_CK(long sGSG_CK) {
		SGSG_CK = sGSG_CK;
	}
	public String getCSPD_CAT() {
		return CSPD_CAT;
	}
	public void setCSPD_CAT(String cSPD_CAT) {
		CSPD_CAT = cSPD_CAT;
	}
	public String getCSPI_ID() {
		return CSPI_ID;
	}
	public void setCSPI_ID(String cSPI_ID) {
		CSPI_ID = cSPI_ID;
	}
	public String getPDPD_ID() {
		return PDPD_ID;
	}
	public void setPDPD_ID(String pDPD_ID) {
		PDPD_ID = pDPD_ID;
	}
	public String getIDCD_ID() {
		return IDCD_ID;
	}
	public void setIDCD_ID(String iDCD_ID) {
		IDCD_ID = iDCD_ID;
	}
	public String getIPCD_ID() {
		return IPCD_ID;
	}
	public void setIPCD_ID(String iPCD_ID) {
		IPCD_ID = iPCD_ID;
	}
	public String getBLXP_ACCT_CAT() {
		return BLXP_ACCT_CAT;
	}
	public void setBLXP_ACCT_CAT(String bLXP_ACCT_CAT) {
		BLXP_ACCT_CAT = bLXP_ACCT_CAT;
	}
	public String getBLXP_EXP_CAT() {
		return BLXP_EXP_CAT;
	}
	public void setBLXP_EXP_CAT(String bLXP_EXP_CAT) {
		BLXP_EXP_CAT = bLXP_EXP_CAT;
	}
	public Date getBLXP_FROM_DT() {
		return BLXP_FROM_DT;
	}
	public void setBLXP_FROM_DT(Date bLXP_FROM_DT) {
		BLXP_FROM_DT = bLXP_FROM_DT;
	}
	public Date getBLXP_TO_DT() {
		return BLXP_TO_DT;
	}
	public void setBLXP_TO_DT(Date bLXP_TO_DT) {
		BLXP_TO_DT = bLXP_TO_DT;
	}
	public Date getBLXP_PAID_DT() {
		return BLXP_PAID_DT;
	}
	public void setBLXP_PAID_DT(Date bLXP_PAID_DT) {
		BLXP_PAID_DT = bLXP_PAID_DT;
	}
	public double getBLXP_PAID_AMT() {
		return BLXP_PAID_AMT;
	}
	public void setBLXP_PAID_AMT(double bLXP_PAID_AMT) {
		BLXP_PAID_AMT = bLXP_PAID_AMT;
	}
	public String getLOBD_ID() {
		return LOBD_ID;
	}
	public void setLOBD_ID(String lOBD_ID) {
		LOBD_ID = lOBD_ID;
	}
	public Date getBLXP_CREATE_DTM() {
		return BLXP_CREATE_DTM;
	}
	public void setBLXP_CREATE_DTM(Date bLXP_CREATE_DTM) {
		BLXP_CREATE_DTM = bLXP_CREATE_DTM;
	}
	public long getBLXP_STS() {
		return BLXP_STS;
	}
	public void setBLXP_STS(long bLXP_STS) {
		BLXP_STS = bLXP_STS;
	}
	public double getBLXP_HSA_PAID() {
		return BLXP_HSA_PAID_AMT;
	}
	public void setBLXP_HSA_PAID(double bLXP_HSA_PAID) {
		BLXP_HSA_PAID_AMT = bLXP_HSA_PAID;
	}
	public int getBLXP_LOCK_TOKEN() {
		return BLXP_LOCK_TOKEN;
	}
	public void setBLXP_LOCK_TOKEN(int bLXP_LOCK_TOKEN) {
		BLXP_LOCK_TOKEN = bLXP_LOCK_TOKEN;
	}
	public Date getATXR_SOURCE_ID() {
		return ATXR_SOURCE_ID;
	}
	public void setATXR_SOURCE_ID(Date aTXR_SOURCE_ID) {
		ATXR_SOURCE_ID = aTXR_SOURCE_ID;
	}
	public String getIDCD_TYPE_NVL() {
		return IDCD_TYPE_NVL;
	}
	public void setIDCD_TYPE_NVL(String iDCD_TYPE_NVL) {
		IDCD_TYPE_NVL = iDCD_TYPE_NVL;
	}
	public String getCSCS_ID_NVL() {
		return CSCS_ID_NVL;
	}
	public void setCSCS_ID_NVL(String cSCS_ID_NVL) {
		CSCS_ID_NVL = cSCS_ID_NVL;
	}

	}
