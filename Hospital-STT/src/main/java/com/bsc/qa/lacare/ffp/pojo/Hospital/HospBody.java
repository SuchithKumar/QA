package com.bsc.qa.lacare.ffp.pojo.Hospital;

import com.github.ffpojo.metadata.positional.annotation.PositionalField;
import com.github.ffpojo.metadata.positional.annotation.PositionalRecord;


@PositionalRecord
public class HospBody {

	private String tran_type;
	private String plan_cd;
	private String product;
	private String eff_dt;
	private String term_dt;
	private String filler1;
	private String addr_line1;
	private String addr_line2;
	private String city;
	private String county_cd;
	private String state_cd;
	private String zip_cd;
	private String phone;
	private String phone_extn;
	private String fax;
	private String filler2;
	private String fed_taxid;
	private String filler3;
	private String prov_type;
	private String site_id;
	private String filler4;
	private String hosp_nm;
	private String medicare;
	private String filler5;
	private String npi;
	private String parent_npi;
	private String taxonomy_cd1;
	private String taxonomy_cd2;
	private String taxonomy_cd3;
	private String taxonomy_cd4;
	private String taxonomy_cd5;
	private String taxonomy_cd6;
	private String taxonomy_cd7;
	private String taxonomy_cd8;
	private String taxonomy_cd9;
	private String taxonomy_cd10;
	private String taxonomy_cd11;
	private String taxonomy_cd12;
	private String taxonomy_cd13;
	private String taxonomy_cd14;
	private String taxonomy_cd15;
	private String filler6;
//	private String error_code;
//	private String error_description;
	
	@PositionalField(initialPosition=1,finalPosition=1)
	public String getTran_type() {
		return tran_type;
	}
	public void setTran_type(String tran_type) {
		this.tran_type = tran_type;
	}
	
	@PositionalField(initialPosition=2,finalPosition=5)
	public String getPlan_cd() {
		return plan_cd;
	}
	public void setPlan_cd(String plan_cd) {
		this.plan_cd = plan_cd;
	}
	
	@PositionalField(initialPosition=6,finalPosition=7)
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	
	@PositionalField(initialPosition=8,finalPosition=15)
	public String getEff_dt() {
		return eff_dt;
	}
	
	public void setEff_dt(String eff_dt) {
		this.eff_dt = eff_dt;
	}
	
	@PositionalField(initialPosition=16,finalPosition=23)
	public String getTerm_dt() {
		return term_dt;
	}
	public void setTerm_dt(String term_dt) {
		this.term_dt = term_dt;
	}
	@PositionalField(initialPosition=24,finalPosition=232)
	public String getFiller1() {
		return filler1;
	}
	public void setFiller1(String filler1) {
		this.filler1 = filler1;
	}
	@PositionalField(initialPosition=233,finalPosition=267)
	public String getAddr_line1() {
		return addr_line1;
	}
	public void setAddr_line1(String addr_line1) {
		this.addr_line1 = addr_line1;
	}
	
	@PositionalField(initialPosition=268,finalPosition=302)
	public String getAddr_line2() {
		return addr_line2;
	}
	public void setAddr_line2(String addr_line2) {
		this.addr_line2 = addr_line2;
	}
	
	@PositionalField(initialPosition=303,finalPosition=322)
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@PositionalField(initialPosition=323,finalPosition=324)
	public String getCounty_cd() {
		return county_cd;
	}
	public void setCounty_cd(String county_cd) {
		this.county_cd = county_cd;
	}
	
	@PositionalField(initialPosition=325,finalPosition=326)
	public String getState_cd() {
		return state_cd;
	}
	public void setState_cd(String state_cd) {
		this.state_cd = state_cd;
	}
	
	@PositionalField(initialPosition=327,finalPosition=331)
	public String getZip_cd() {
		return zip_cd;
	}
	public void setZip_cd(String zip_cd) {
		this.zip_cd = zip_cd;
	}
	
	@PositionalField(initialPosition=337,finalPosition=346)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@PositionalField(initialPosition=347,finalPosition=351)
	public String getPhone_extn() {
		return phone_extn;
	}
	public void setPhone_extn(String phone_extn) {
		this.phone_extn = phone_extn;
	}
	
	@PositionalField(initialPosition=352,finalPosition=361)
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	@PositionalField(initialPosition=362,finalPosition=490)
	public String getFiller2() {
		return filler2;
	}
	public void setFiller2(String filler2) {
		this.filler2 = filler2;
	}
	@PositionalField(initialPosition=491,finalPosition=505)
	public String getFed_taxid() {
		return fed_taxid;
	}
	public void setFed_taxid(String fed_taxid) {
		this.fed_taxid = fed_taxid;
	}
	@PositionalField(initialPosition=506,finalPosition=965)
	public String getFiller3() {
		return filler3;
	}
	public void setFiller3(String filler3) {
		this.filler3 = filler3;
	}
	@PositionalField(initialPosition=966,finalPosition=967)
	public String getProv_type() {
		return prov_type;
	}
	public void setProv_type(String prov_type) {
		this.prov_type = prov_type;
	}
	
	@PositionalField(initialPosition=968,finalPosition=971)
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}
	@PositionalField(initialPosition=983,finalPosition=1033)
	public String getFiller4() {
		return filler4;
	}
	public void setFiller4(String filler4) {
		this.filler4 = filler4;
	}
	@PositionalField(initialPosition=1034,finalPosition=1083)
	public String getHosp_nm() {
		return hosp_nm;
	}
	public void setHosp_nm(String hosp_nm) {
		this.hosp_nm = hosp_nm;
	}
	
	@PositionalField(initialPosition=1084,finalPosition=1089)
	public String getMedicare() {
		return medicare;
	}
	public void setMedicare(String medicare) {
		this.medicare = medicare;
	}

	@PositionalField(initialPosition=1090,finalPosition=1288)
	public String getFiller5() {
		return filler5;
	}
	public void setFiller5(String filler5) {
		this.filler5 = filler5;
	}
	@PositionalField(initialPosition=1289,finalPosition=1298)
	public String getNpi() {
		return npi;
	}
	public void setNpi(String npi) {
		this.npi = npi;
	}
	
	@PositionalField(initialPosition=1299,finalPosition=1308)
	public String getParent_npi() {
		return parent_npi;
	}
	public void setParent_npi(String parent_npi) {
		this.parent_npi = parent_npi;
	}
	
	@PositionalField(initialPosition=1309,finalPosition=1318)
	public String getTaxonomy_cd1() {
		return taxonomy_cd1;
	}
	public void setTaxonomy_cd1(String taxonomy_cd1) {
		this.taxonomy_cd1 = taxonomy_cd1;
	}
	
	@PositionalField(initialPosition=1319,finalPosition=1328)
	public String getTaxonomy_cd2() {
		return taxonomy_cd2;
	}
	public void setTaxonomy_cd2(String taxonomy_cd2) {
		this.taxonomy_cd2 = taxonomy_cd2;
	}
	
	@PositionalField(initialPosition=1329,finalPosition=1338)
	public String getTaxonomy_cd3() {
		return taxonomy_cd3;
	}
	public void setTaxonomy_cd3(String taxonomy_cd3) {
		this.taxonomy_cd3 = taxonomy_cd3;
	}
	
	@PositionalField(initialPosition=1339,finalPosition=1348)
	public String getTaxonomy_cd4() {
		return taxonomy_cd4;
	}
	public void setTaxonomy_cd4(String taxonomy_cd4) {
		this.taxonomy_cd4 = taxonomy_cd4;
	}
	@PositionalField(initialPosition=1349,finalPosition=1358)
	public String getTaxonomy_cd5() {
		return taxonomy_cd5;
	}
	public void setTaxonomy_cd5(String taxonomy_cd5) {
		this.taxonomy_cd5 = taxonomy_cd5;
	}
	@PositionalField(initialPosition=1359,finalPosition=1368)
	public String getTaxonomy_cd6() {
		return taxonomy_cd6;
	}
	public void setTaxonomy_cd6(String taxonomy_cd6) {
		this.taxonomy_cd6 = taxonomy_cd6;
	}
	
	@PositionalField(initialPosition=1369,finalPosition=1378)
	public String getTaxonomy_cd7() {
		return taxonomy_cd7;
	}
	public void setTaxonomy_cd7(String taxonomy_cd7) {
		this.taxonomy_cd7 = taxonomy_cd7;
	}
	
	@PositionalField(initialPosition=1379,finalPosition=1388)
	public String getTaxonomy_cd8() {
		return taxonomy_cd8;
	}
	public void setTaxonomy_cd8(String taxonomy_cd8) {
		this.taxonomy_cd8 = taxonomy_cd8;
	}
	
	@PositionalField(initialPosition=1389,finalPosition=1398)
	public String getTaxonomy_cd9() {
		return taxonomy_cd9;
	}
	public void setTaxonomy_cd9(String taxonomy_cd9) {
		this.taxonomy_cd9 = taxonomy_cd9;
	}
	
	@PositionalField(initialPosition=1399,finalPosition=1408)
	public String getTaxonomy_cd10() {
		return taxonomy_cd10;
	}
	public void setTaxonomy_cd10(String taxonomy_cd10) {
		this.taxonomy_cd10 = taxonomy_cd10;
	}
	
	@PositionalField(initialPosition=1409,finalPosition=1418)
	public String getTaxonomy_cd11() {
		return taxonomy_cd11;
	}
	public void setTaxonomy_cd11(String taxonomy_cd11) {
		this.taxonomy_cd11 = taxonomy_cd11;
	}
	
	@PositionalField(initialPosition=1419,finalPosition=1428)
	public String getTaxonomy_cd12() {
		return taxonomy_cd12;
	}
	public void setTaxonomy_cd12(String taxonomy_cd12) {
		this.taxonomy_cd12 = taxonomy_cd12;
	}
	
	@PositionalField(initialPosition=1429,finalPosition=1438)
	public String getTaxonomy_cd13() {
		return taxonomy_cd13;
	}
	public void setTaxonomy_cd13(String taxonomy_cd13) {
		this.taxonomy_cd13 = taxonomy_cd13;
	}
	
	@PositionalField(initialPosition=1439,finalPosition=1448)
	public String getTaxonomy_cd14() {
		return taxonomy_cd14;
	}
	public void setTaxonomy_cd14(String taxonomy_cd14) {
		this.taxonomy_cd14 = taxonomy_cd14;
	}
	
	@PositionalField(initialPosition=1449,finalPosition=1458)
	public String getTaxonomy_cd15() {
		return taxonomy_cd15;
	}
	public void setTaxonomy_cd15(String taxonomy_cd15) {
		this.taxonomy_cd15 = taxonomy_cd15;
	}
	@PositionalField(initialPosition=1459,finalPosition=1500)
	public String getFiller6() {
		return filler6;
	}
	public void setFiller6(String filler6) {
		this.filler6 = filler6;
	}
	/*
	@PositionalField(initialPosition=1511,finalPosition=1523)
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	
	@PositionalField(initialPosition=1524,finalPosition=1610)
	public String getError_description() {
		return error_description;
	}
	public void setError_description(String error_description) {
		this.error_description = error_description;
	}
*/
	
	

	
	
	
}
