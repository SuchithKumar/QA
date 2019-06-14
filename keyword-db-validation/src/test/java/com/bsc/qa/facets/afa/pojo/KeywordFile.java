package com.bsc.qa.facets.afa.pojo;

import com.github.ffpojo.metadata.positional.annotation.PositionalField;
import com.github.ffpojo.metadata.positional.annotation.PositionalRecord;

@PositionalRecord
public class KeywordFile{
	private String record_type;
	private String run_date;
	private String batch_name;
	private String sequence_number;
	private String claim_number;
	private String line_number;
	private String group_id;
	private String subscriber_id;
	private String relationship_code;
	private String member_suffix;
	private String plan_category;
	private String plan_id;
	private String class_id;
	private String diagnosis_code_type;
	private String procedure_code;
	private String earliest_service_date;
	private String latest_service_date;
	private String paid_amount;
	private String record_number;
	private String accounting_category;
	private String experience_category;
	private String hsa_paid_amount;
	private String diagnosis_code;
	
	@PositionalField(initialPosition = 1,finalPosition=4)
	public String getRecord_type() {
		return record_type;
	}
	public void setRecord_type(String record_type) {
		this.record_type = record_type;
	}
	
	@PositionalField(initialPosition = 5, finalPosition = 10)
	public String getRun_date() {
		return run_date;
	}
	public void setRun_date(String run_date) {
		this.run_date = run_date;
	}
	
	@PositionalField(initialPosition = 11, finalPosition = 17)
	public String getBatch_name() {
		return batch_name;
	}
	public void setBatch_name(String batch_name) {
		this.batch_name = batch_name;
	}
	
	@PositionalField(initialPosition = 18, finalPosition = 19)
	public String getSequence_number() {
		return sequence_number;
	}
	public void setSequence_number(String sequence_number) {
		this.sequence_number = sequence_number;
	}
	
	@PositionalField(initialPosition = 20, finalPosition = 31)
	public String getClaim_number() {
		return claim_number;
	}
	public void setClaim_number(String claim_number) {
		this.claim_number = claim_number;
	}
	
	@PositionalField(initialPosition = 32, finalPosition = 33)
	public String getLine_number() {
		return line_number;
	}
	public void setLine_number(String line_number) {
		this.line_number = line_number;
	}
	
	@PositionalField(initialPosition = 34, finalPosition = 41)
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	
	@PositionalField(initialPosition = 42, finalPosition = 50)
	public String getSubscriber_id() {
		return subscriber_id;
	}
	public void setSubscriber_id(String subscriber_id) {
		this.subscriber_id = subscriber_id;
	}
	
	@PositionalField(initialPosition = 51, finalPosition = 51)
	public String getRelationship_code() {
		return relationship_code;
	}
	public void setRelationship_code(String relationship_code) {
		this.relationship_code = relationship_code;
	}
	
	@PositionalField(initialPosition = 52, finalPosition = 53)
	public String getMember_suffix() {
		return member_suffix;
	}
	public void setMember_suffix(String member_suffix) {
		this.member_suffix = member_suffix;
	}
	
	@PositionalField(initialPosition = 54, finalPosition = 54)
	public String getPlan_category() {
		return plan_category;
	}
	public void setPlan_category(String plan_category) {
		this.plan_category = plan_category;
	}
	
	@PositionalField(initialPosition = 55, finalPosition = 62)
	public String getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}
	
	@PositionalField(initialPosition = 63, finalPosition = 66)
	public String getClass_id() {
		return class_id;
	}
	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
	
	@PositionalField(initialPosition = 67, finalPosition = 67)
	public String getDiagnosis_code_type() {
		return diagnosis_code_type;
	}
	public void setDiagnosis_code_type(String diagnosis_code_type) {
		this.diagnosis_code_type = diagnosis_code_type;
	}
	
	@PositionalField(initialPosition = 69, finalPosition = 75)
	public String getProcedure_code() {
		return procedure_code;
	}
	public void setProcedure_code(String procedure_code) {
		this.procedure_code = procedure_code;
	}
	
	@PositionalField(initialPosition = 76, finalPosition = 83)
	public String getEarliest_service_date() {
		return earliest_service_date;
	}
	public void setEarliest_service_date(String earliest_service_date) {
		this.earliest_service_date = earliest_service_date;
	}
	
	@PositionalField(initialPosition = 84, finalPosition =91)
	public String getLatest_service_date() {
		return latest_service_date;
	}
	public void setLatest_service_date(String latest_service_date) {
		this.latest_service_date = latest_service_date;
	}
	
	@PositionalField(initialPosition = 92, finalPosition = 111)
	public String getPaid_amount() {
		return paid_amount;
	}
	public void setPaid_amount(String paid_amount) {
		this.paid_amount = paid_amount;
	}
	
	@PositionalField(initialPosition = 112, finalPosition = 119)
	public String getRecord_number() {
		return record_number;
	}
	public void setRecord_number(String record_number) {
		this.record_number = record_number;
	}
	
	@PositionalField(initialPosition = 120, finalPosition = 123)
	public String getAccounting_category() {
		return accounting_category;
	}
	public void setAccounting_category(String accounting_category) {
		this.accounting_category = accounting_category;
	}
	
	@PositionalField(initialPosition = 124, finalPosition = 127)
	public String getExperience_category() {
		return experience_category;
	}
	public void setExperience_category(String experience_category) {
		this.experience_category = experience_category;
	}
	
	@PositionalField(initialPosition = 130, finalPosition = 149)
	public String getHsa_paid_amount() {
		return hsa_paid_amount;
	}
	public void setHsa_paid_amount(String hsa_paid_amount) {
		this.hsa_paid_amount = hsa_paid_amount;
	}
	
	@PositionalField(initialPosition = 150, finalPosition = 159)
	public String getDiagnosis_code() {
		return diagnosis_code;
	}
	public void setDiagnosis_code(String diagnosis_code) {
		this.diagnosis_code = diagnosis_code;
	}
	

	
}
