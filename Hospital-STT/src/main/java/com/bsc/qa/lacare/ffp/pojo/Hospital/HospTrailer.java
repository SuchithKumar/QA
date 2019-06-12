package com.bsc.qa.lacare.ffp.pojo.Hospital;

import com.github.ffpojo.metadata.positional.annotation.PositionalField;
import com.github.ffpojo.metadata.positional.annotation.PositionalRecord;

@PositionalRecord
public class HospTrailer {
	private String rec_type;
	private String plan_cd;
	private String create_dt;
	private String eff_dt;
	private String rec_count;
	@PositionalField(initialPosition=1,finalPosition=1)
	public String getRec_type() {
		return rec_type;
	}
	public void setRec_type(String rec_type) {
		this.rec_type = rec_type;
	}
	@PositionalField(initialPosition=2,finalPosition=5)
	public String getPlan_cd() {
		return plan_cd;
	}
	public void setPlan_cd(String plan_cd) {
		this.plan_cd = plan_cd;
	}
	@PositionalField(initialPosition=6,finalPosition=13)
	public String getCreate_dt() {
		return create_dt;
	}
	public void setCreate_dt(String create_dt) {
		this.create_dt = create_dt;
	}
	@PositionalField(initialPosition=14,finalPosition=21)
	public String getEff_dt() {
		return eff_dt;
	}
	public void setEff_dt(String eff_dt) {
		this.eff_dt = eff_dt;
	}
	@PositionalField(initialPosition=22,finalPosition=28)
	public String getRec_count() {
		return rec_count;
	}
	public void setRec_count(String rec_count) {
		this.rec_count = rec_count;
	}
	
	
}
