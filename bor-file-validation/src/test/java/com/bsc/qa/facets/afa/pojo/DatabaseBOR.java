package com.bsc.qa.facets.afa.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import oracle.sql.TIMESTAMP;

@Entity
@Table(name = "FACETS_CUSTOM.ARGUS_GL_BOR_FIL_HIST")
public class DatabaseBOR {

	@Id
	private String FICT_CLM_ID;
	private String FIL_NM;
	private String VEND_NM;
	private String GRP_NBR;
	private String SBGRP_ID;
	private String SBSCR_ID;
	private String PERS_NBR;
	private String CLM_NBR;
	private String CLM_VER_NBR;
	private BigDecimal CLM_AMT;
	private BigDecimal CLI_PRC_AMT;
	private BigDecimal BSC_RVNU_AMT;
	private String CHK_NBR;
	private Date CHK_DT;
	private Date SVC_DT;
	private String PAYE_ID;
	private String PAYE_NM;
	private String PLN_ID;
	private String PRDCT_ID;
	private String PRDCT_CATEG_CD;
	private String CLS_ID;
	private String PRDCT_BUS_CATEG_CD;
	private String PRDCT_VAL1_CD;
	private String LOB_ID;
	private String LGL_ENTY_CD;
	private BigDecimal BIL_AMT;
	private BigDecimal ALLOW_AMT;
	private BigDecimal DED_AMT;
	private BigDecimal COINS_AMT;
	private BigDecimal COPAY_AMT;
	private String DIAG_CD;
	private String DIAG_TYP_CD;
	private String PROC_CD;
	private String HCPCS_ID;
	private String CLM_TRANS_TYP_CD;
	private String ERR_CD;
	private String ERR_DESC;
	private BigDecimal SEQ_KEY;
	public String getFICT_CLM_ID() {
		return FICT_CLM_ID;
	}
	public void setFICT_CLM_ID(String fICT_CLM_ID) {
		FICT_CLM_ID = fICT_CLM_ID;
	}
	public String getFIL_NM() {
		return FIL_NM;
	}
	public void setFIL_NM(String fIL_NM) {
		FIL_NM = fIL_NM;
	}
	public String getVEND_NM() {
		return VEND_NM;
	}
	public void setVEND_NM(String vEND_NM) {
		VEND_NM = vEND_NM;
	}
	public String getGRP_NBR() {
		return GRP_NBR;
	}
	public void setGRP_NBR(String gRP_NBR) {
		GRP_NBR = gRP_NBR;
	}
	public String getSBGRP_ID() {
		return SBGRP_ID;
	}
	public void setSBGRP_ID(String sBGRP_ID) {
		SBGRP_ID = sBGRP_ID;
	}
	public String getSBSCR_ID() {
		return SBSCR_ID;
	}
	public void setSBSCR_ID(String sBSCR_ID) {
		SBSCR_ID = sBSCR_ID;
	}
	public String getPERS_NBR() {
		return PERS_NBR;
	}
	public void setPERS_NBR(String pERS_NBR) {
		PERS_NBR = pERS_NBR;
	}
	public String getCLM_NBR() {
		return CLM_NBR;
	}
	public void setCLM_NBR(String cLM_NBR) {
		CLM_NBR = cLM_NBR;
	}
	public String getCLM_VER_NBR() {
		return CLM_VER_NBR;
	}
	public void setCLM_VER_NBR(String cLM_VER_NBR) {
		CLM_VER_NBR = cLM_VER_NBR;
	}
	public BigDecimal getCLM_AMT() {
		return CLM_AMT;
	}
	public void setCLM_AMT(BigDecimal cLM_AMT) {
		CLM_AMT = cLM_AMT;
	}
	public BigDecimal getCLI_PRC_AMT() {
		return CLI_PRC_AMT;
	}
	public void setCLI_PRC_AMT(BigDecimal cLI_PRC_AMT) {
		CLI_PRC_AMT = cLI_PRC_AMT;
	}
	public BigDecimal getBSC_RVNU_AMT() {
		return BSC_RVNU_AMT;
	}
	public void setBSC_RVNU_AMT(BigDecimal bSC_RVNU_AMT) {
		BSC_RVNU_AMT = bSC_RVNU_AMT;
	}
	public String getCHK_NBR() {
		return CHK_NBR;
	}
	public void setCHK_NBR(String cHK_NBR) {
		CHK_NBR = cHK_NBR;
	}
	public Date getCHK_DT() {
		return CHK_DT;
	}
	public void setCHK_DT(Date cHK_DT) {
		CHK_DT = cHK_DT;
	}
	public Date getSVC_DT() {
		return SVC_DT;
	}
	public void setSVC_DT(Date sVC_DT) {
		SVC_DT = sVC_DT;
	}
	public String getPAYE_ID() {
		return PAYE_ID;
	}
	public void setPAYE_ID(String pAYE_ID) {
		PAYE_ID = pAYE_ID;
	}
	public String getPAYE_NM() {
		return PAYE_NM;
	}
	public void setPAYE_NM(String pAYE_NM) {
		PAYE_NM = pAYE_NM;
	}
	public String getPLN_ID() {
		return PLN_ID;
	}
	public void setPLN_ID(String pLN_ID) {
		PLN_ID = pLN_ID;
	}
	public String getPRDCT_ID() {
		return PRDCT_ID;
	}
	public void setPRDCT_ID(String pRDCT_ID) {
		PRDCT_ID = pRDCT_ID;
	}
	public String getPRDCT_CATEG_CD() {
		return PRDCT_CATEG_CD;
	}
	public void setPRDCT_CATEG_CD(String pRDCT_CATEG_CD) {
		PRDCT_CATEG_CD = pRDCT_CATEG_CD;
	}
	public String getCLS_ID() {
		return CLS_ID;
	}
	public void setCLS_ID(String cLS_ID) {
		CLS_ID = cLS_ID;
	}
	public String getPRDCT_BUS_CATEG_CD() {
		return PRDCT_BUS_CATEG_CD;
	}
	public void setPRDCT_BUS_CATEG_CD(String pRDCT_BUS_CATEG_CD) {
		PRDCT_BUS_CATEG_CD = pRDCT_BUS_CATEG_CD;
	}
	public String getPRDCT_VAL1_CD() {
		return PRDCT_VAL1_CD;
	}
	public void setPRDCT_VAL1_CD(String pRDCT_VAL1_CD) {
		PRDCT_VAL1_CD = pRDCT_VAL1_CD;
	}
	public String getLOB_ID() {
		return LOB_ID;
	}
	public void setLOB_ID(String lOB_ID) {
		LOB_ID = lOB_ID;
	}
	public String getLGL_ENTY_CD() {
		return LGL_ENTY_CD;
	}
	public void setLGL_ENTY_CD(String lGL_ENTY_CD) {
		LGL_ENTY_CD = lGL_ENTY_CD;
	}
	public BigDecimal getBIL_AMT() {
		return BIL_AMT;
	}
	public void setBIL_AMT(BigDecimal bIL_AMT) {
		BIL_AMT = bIL_AMT;
	}
	public BigDecimal getALLOW_AMT() {
		return ALLOW_AMT;
	}
	public void setALLOW_AMT(BigDecimal aLLOW_AMT) {
		ALLOW_AMT = aLLOW_AMT;
	}
	public BigDecimal getDED_AMT() {
		return DED_AMT;
	}
	public void setDED_AMT(BigDecimal dED_AMT) {
		DED_AMT = dED_AMT;
	}
	public BigDecimal getCOINS_AMT() {
		return COINS_AMT;
	}
	public void setCOINS_AMT(BigDecimal cOINS_AMT) {
		COINS_AMT = cOINS_AMT;
	}
	public BigDecimal getCOPAY_AMT() {
		return COPAY_AMT;
	}
	public void setCOPAY_AMT(BigDecimal cOPAY_AMT) {
		COPAY_AMT = cOPAY_AMT;
	}
	public String getDIAG_CD() {
		return DIAG_CD;
	}
	public void setDIAG_CD(String dIAG_CD) {
		DIAG_CD = dIAG_CD;
	}
	public String getDIAG_TYP_CD() {
		return DIAG_TYP_CD;
	}
	public void setDIAG_TYP_CD(String dIAG_TYP_CD) {
		DIAG_TYP_CD = dIAG_TYP_CD;
	}
	public String getPROC_CD() {
		return PROC_CD;
	}
	public void setPROC_CD(String pROC_CD) {
		PROC_CD = pROC_CD;
	}
	public String getHCPCS_ID() {
		return HCPCS_ID;
	}
	public void setHCPCS_ID(String hCPCS_ID) {
		HCPCS_ID = hCPCS_ID;
	}
	public String getCLM_TRANS_TYP_CD() {
		return CLM_TRANS_TYP_CD;
	}
	public void setCLM_TRANS_TYP_CD(String cLM_TRANS_TYP_CD) {
		CLM_TRANS_TYP_CD = cLM_TRANS_TYP_CD;
	}
	public String getERR_CD() {
		return ERR_CD;
	}
	public void setERR_CD(String eRR_CD) {
		ERR_CD = eRR_CD;
	}
	public String getERR_DESC() {
		return ERR_DESC;
	}
	public void setERR_DESC(String eRR_DESC) {
		ERR_DESC = eRR_DESC;
	}

	public BigDecimal getSEQ_KEY() {
		return SEQ_KEY;
	}
	public void setSEQ_KEY(BigDecimal sEQ_KEY) {
		SEQ_KEY = sEQ_KEY;
	}
	@Override
	public String toString() {
		return "DatabaseBOR [FICT_CLM_ID=" + FICT_CLM_ID + ", FIL_NM=" + FIL_NM
				+ ", VEND_NM=" + VEND_NM + ", GRP_NBR=" + GRP_NBR
				+ ", SBGRP_ID=" + SBGRP_ID + ", SBSCR_ID=" + SBSCR_ID
				+ ", PERS_NBR=" + PERS_NBR + ", CLM_NBR=" + CLM_NBR
				+ ", CLM_VER_NBR=" + CLM_VER_NBR + ", CLM_AMT=" + CLM_AMT
				+ ", CLI_PRC_AMT=" + CLI_PRC_AMT + ", BSC_RVNU_AMT="
				+ BSC_RVNU_AMT + ", CHK_NBR=" + CHK_NBR + ", CHK_DT=" + CHK_DT
				+ ", SVC_DT=" + SVC_DT + ", PAYE_ID=" + PAYE_ID + ", PAYE_NM="
				+ PAYE_NM + ", PLN_ID=" + PLN_ID + ", PRDCT_ID=" + PRDCT_ID
				+ ", PRDCT_CATEG_CD=" + PRDCT_CATEG_CD + ", CLS_ID=" + CLS_ID
				+ ", PRDCT_BUS_CATEG_CD=" + PRDCT_BUS_CATEG_CD
				+ ", PRDCT_VAL1_CD=" + PRDCT_VAL1_CD + ", LOB_ID=" + LOB_ID
				+ ", LGL_ENTY_CD=" + LGL_ENTY_CD + ", BIL_AMT=" + BIL_AMT
				+ ", ALLOW_AMT=" + ALLOW_AMT + ", DED_AMT=" + DED_AMT
				+ ", COINS_AMT=" + COINS_AMT + ", COPAY_AMT=" + COPAY_AMT
				+ ", DIAG_CD=" + DIAG_CD + ", DIAG_TYP_CD=" + DIAG_TYP_CD
				+ ", PROC_CD=" + PROC_CD + ", HCPCS_ID=" + HCPCS_ID
				+ ", CLM_TRANS_TYP_CD=" + CLM_TRANS_TYP_CD + ", ERR_CD="
				+ ERR_CD + ", ERR_DESC=" + ERR_DESC + ", SEQ_KEY="
				+ SEQ_KEY + "]";
	}
	
	

}
