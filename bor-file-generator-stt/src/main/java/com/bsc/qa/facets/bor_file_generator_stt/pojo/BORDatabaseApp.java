package com.bsc.qa.facets.bor_file_generator_stt.pojo;

import java.math.BigDecimal;

public class BORDatabaseApp {

	/*
	 * GRPID SUBGRPID SUBID MEMSFX PLANID PRDID PRDCAT CLASSID PRDBUSCAT
	 * PRDVALCD LOBDID LOBDTYPE
	 */
	String grpId;
	String subgrpId;
	String subId;
	BigDecimal memSfx;
	String planId;
	String prdId;
	String prdCat;
	String classId;
	String prdBusCat;
	String prdValCD;
	String lobdId;
	String lobType;

	public String getGrpId() {
		return grpId;
	}

	public void setGrpId(String grpId) {
		this.grpId = grpId;
	}

	public String getSubgrpId() {
		return subgrpId;
	}

	public void setSubgrpId(String subgrpId) {
		this.subgrpId = subgrpId;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public BigDecimal getMemSfx() {
		return memSfx;
	}

	public void setMemSfx(BigDecimal memSfx) {
		this.memSfx = memSfx;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getPrdId() {
		return prdId;
	}

	public void setPrdId(String prdId) {
		this.prdId = prdId;
	}

	public String getPrdCat() {
		return prdCat;
	}

	public void setPrdCat(String prdCat) {
		this.prdCat = prdCat;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getPrdBusCat() {
		return prdBusCat;
	}

	public void setPrdBusCat(String prdBusCat) {
		this.prdBusCat = prdBusCat;
	}

	public String getPrdValCD() {
		return prdValCD;
	}

	public void setPrdValCD(String prdValCD) {
		this.prdValCD = prdValCD;
	}

	public String getLobdId() {
		return lobdId;
	}

	public void setLobdId(String lobdId) {
		this.lobdId = lobdId;
	}

	public String getLobType() {
		return lobType;
	}

	public void setLobType(String lobType) {
		this.lobType = lobType;
	}

	@Override
	public String toString() {
		return "BORDatabase [grpId=" + grpId + ", subgrpId=" + subgrpId
				+ ", subId=" + subId + ", memSfx=" + memSfx + ", planId="
				+ planId + ", prdId=" + prdId + ", prdCat=" + prdCat
				+ ", classId=" + classId + ", prdBusCat=" + prdBusCat
				+ ", prdValCD=" + prdValCD + ", lobdId=" + lobdId
				+ ", lobType=" + lobType + "]";
	}

}
