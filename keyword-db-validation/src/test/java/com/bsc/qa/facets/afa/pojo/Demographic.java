package com.bsc.qa.facets.afa.pojo;

import java.math.BigDecimal;

public class Demographic {
	
	private BigDecimal SBSB_CK;
	private BigDecimal MEME_CK;
	private BigDecimal GRGR_CK;
	private String PRDCT_ID;
	private String LOB_ID;
	private BigDecimal MEME_SFX;
	private String MEME_REL;
	
	public String getMEME_REL() {
		return MEME_REL;
	}
	public void setMEME_REL(String mEME_REL) {
		MEME_REL = mEME_REL;
	}
	public BigDecimal getMEME_SFX() {
		return MEME_SFX;
	}
	public void setMEME_SFX(BigDecimal mEME_SFX) {
		MEME_SFX = mEME_SFX;
	}
	public BigDecimal getSBSB_CK() {
		return SBSB_CK;
	}
	public void setSBSB_CK(BigDecimal sBSB_CK) {
		SBSB_CK = sBSB_CK;
	}
	public BigDecimal getMEME_CK() {
		return MEME_CK;
	}
	public void setMEME_CK(BigDecimal mEME_CK) {
		MEME_CK = mEME_CK;
	}
	public BigDecimal getGRGR_CK() {
		return GRGR_CK;
	}
	public void setGRGR_CK(BigDecimal gRGR_CK) {
		GRGR_CK = gRGR_CK;
	}
	public String getPRDCT_ID() {
		return PRDCT_ID;
	}
	public void setPRDCT_ID(String pRDCT_ID) {
		PRDCT_ID = pRDCT_ID;
	}
	public String getLOB_ID() {
		return LOB_ID;
	}
	public void setLOB_ID(String lOB_ID) {
		LOB_ID = lOB_ID;
	}
}
