package com.bsc.qa.lacare.ffp.pojo.Hospital;

import com.github.ffpojo.metadata.positional.annotation.PositionalField;
import com.github.ffpojo.metadata.positional.annotation.PositionalRecord;

@PositionalRecord
public class HospHeader {
	private String record_type;
	private String plan_cd;
	private String create_dt;
	private String file_type;
	private String del_char;
	private String eff_dt;
	private String rec_count;
	private String comment;
	
	@PositionalField(initialPosition=1,finalPosition=1)
	public String getRecord_type() {
		return record_type;
	}
	public void setRecord_type(String record_type) {
		this.record_type = record_type;
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
	@PositionalField(initialPosition=14,finalPosition=17)
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}
	@PositionalField(initialPosition=18,finalPosition=18)
	public String getDel_char() {
		return del_char;
	}
	public void setDel_char(String del_char) {
		this.del_char = del_char;
	}
	@PositionalField(initialPosition=19,finalPosition=26)
	public String getEff_dt() {
		return eff_dt;
	}
	public void setEff_dt(String eff_dt) {
		this.eff_dt = eff_dt;
	}
	@PositionalField(initialPosition=27,finalPosition=33)
	public String getRec_count() {
		return rec_count;
	}
	public void setRec_count(String rec_count) {
		this.rec_count = rec_count;
	}
	//@PositionalField(initialPosition=34,finalPosition=1501)
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

}
