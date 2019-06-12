package com.bsc.qa.lacare.db;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.bsc.qa.lacare.ffp.ffpojo.reader.HospitalFileReader;
import com.bsc.qa.lacare.ffp.pojo.Hospital.HospBody;
import com.bsc.qa.lacare.ffp.pojo.Hospital.HospHeader;
import com.bsc.qa.lacare.pojo.Connection;
import com.bsc.qa.lacare.pojo.Database;
import com.bsc.qa.lacare.pojo.PartnerRawDB;
import com.bsc.qa.lacare.pojo.User;
import com.bsc.qa.lacare.util.HibernateUtil;

/**
 * @author jgupta03
 *
 */
public class DatabaseQueries {

	PartnerRawDB partner;
	String headerDate;
	String CreatedDate;
	List<HospBody> parsedbodylist;
	HospHeader bodyHeaderList;
	List<PartnerRawDB> rawlist;
	List<Database> databaselist;
	static Logger logger = LogManager.getLogger(DatabaseQueries.class);

	public static void main(String[] args) {
		DatabaseQueries dq = new DatabaseQueries();
		Connection con = new Connection();
		
		con.setUsername(User.username);
		con.setPassword(User.password);
		con.setUrl(User.url);
		SessionFactory factory = HibernateUtil.createSessionFactory(con);
		Session session = factory.openSession();
		session.beginTransaction();

		List<PartnerRawDB> list = dq.getRawData(session);
		System.out.println("List of raw db" + list.size());
		dq.getDatabaseData(session, list);
		for (PartnerRawDB partnerRawDB : list) {
			System.out.println(partnerRawDB.getPIMS_PROV_TIN_LOC_ID() + "  "
					+ partnerRawDB.getPIMS_PROV_ID());
		}
	}

	public List<PartnerRawDB> getRawData(Session session) {
		HospitalFileReader reader = new HospitalFileReader();
		rawlist = new ArrayList<PartnerRawDB>();

		parsedbodylist = reader.getHospitalBodyData();
		bodyHeaderList = reader.getHospitalHeaderData();
		if (parsedbodylist.isEmpty() && bodyHeaderList == null) {
			System.out.println("File is empty");
		} else {
			// CreatedDate = "01-MAY-2019";
			// CreatedDate = UI.datecreated;
			String dt = bodyHeaderList.getCreate_dt();
			int year = Integer.parseInt(dt.substring(0, 4));
			int month = Integer.parseInt(dt.substring(4, 6));
			int createdDate = Integer.parseInt(dt.substring(6, 8));
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
			Date dte = new Date(year, month - 1, createdDate);
			CreatedDate = sdf.format(dte).toUpperCase();

			Query query = session
					.createSQLQuery("Select PIMS_PROV_ID PROV_ID, PIMS_NET_ID NET_ID, PIMS_PROV_TIN_LOC_ID LOC_ID, LAC_BATCH_ID BATCH_ID, DATE_CREATED, SITE_ID "
							+ "From PARTNER_RAW.LAC_HOSPITAL Where LAC_BATCH_ID = "
							+ "(Select DISTINCT(LAC_BATCH_ID) from PARTNER_RAW.LAC_HOSPITAL "
							+ "Where DATE_CREATED like '"
							+ CreatedDate
							+ "' And ROWNUM = 1) "
							+ "And BSC_MISSING_REQUIRED_IND = 'N' And LENGTH(SITE_ID) < 12");

			try {
				List<Object[]> list = (List<Object[]>) query.list();
				if (list.size() == 0) {
					throw new NullPointerException("No Data Present in the Database");
				} else {
					for (int k = 0; k < list.size(); k++) {

						PartnerRawDB raw = new PartnerRawDB();
						raw.setPIMS_PROV_ID((BigDecimal) (list.get(k)[0]));
						raw.setPIMS_NET_ID((BigDecimal) (list.get(k)[1]));
						raw.setLAC_BATCH_ID((BigDecimal) (list.get(k)[3]));
						raw.setPIMS_PROV_TIN_LOC_ID((BigDecimal) (list.get(k)[2]));
						raw.setDATE_CREATED((Date) (list.get(k)[4]));
						raw.setSITE_ID((String) list.get(k)[5]);
						rawlist.add(raw);
					}
				}
			} catch (Exception e) {
				System.out.println("Data Not present in Database with "
						+ CreatedDate);
				logger.error("Data Not present in Database with " + CreatedDate+e);
				// TODO: handle exception
			}
		}
		return rawlist;
	}

	public List<Database> getDatabaseData(Session session,
			List<PartnerRawDB> rawlist) {
		if (rawlist.size() == 0) {
			System.out.println("No Data present in list");
		} else if (parsedbodylist.size() > 0) {
			databaselist = new ArrayList<Database>();
			String zip, plus;
			System.out.println(parsedbodylist.size());

			for (int i = 0; i < parsedbodylist.size(); i++) {
				Database database = new Database();

				// Trans Type-----on hold(Waiting for Query)
				database.setTran_type(parsedbodylist.get(i).getTran_type());

				// Plan_CD
				Query plancdquery = session
						.createSQLQuery("select DISTINCT(PLAN_CD) from PFI.PFI_LACARE_XWALK where PIMS_PROV_ID ="
								+ rawlist.get(i).getPIMS_PROV_ID());
				if (!plancdquery.list().isEmpty())
					database.setPlan_cd((String) plancdquery.list().get(0));
				else
					database.setPlan_cd("");

				// Product
				database.setProduct("");

				// Eff_Dt
				Date dt = rawlist.get(i).getDATE_CREATED();
				System.out.println("Date----------->" + dt);
				SimpleDateFormat format = new SimpleDateFormat("YYYYMMdd");
				System.out.println("Formated Date--------->"
						+ format.format(dt));
				dt.setMonth(dt.getMonth() + 1);
				dt.setDate(1);
				if (database.getTran_type().equalsIgnoreCase("A"))
					database.setEff_dt(format.format(dt));
				else
					database.setEff_dt("");

				// Term_Dt
				if (database.getTran_type().equalsIgnoreCase("D")) {
					database.setTerm_dt(format.format(dt));
				} else
					database.setTerm_dt("");

				// Address Query
				Query addressquery = session
						.createSQLQuery("SELECT DISTINCT pa.ADDR1 ADDR1, pa.ADDR2 ADDR2,ct.DS CITY, st.STATE State, county274Xref1.CODE COUNTY_CD, pa.ZIP Zip5, pa.PLUS4 FROM PIMS.PP_PROV_LOC pvl INNER JOIN PIMS.PP_PROV pp_prov ON pvl.PROV_ID = pp_prov.ID INNER JOIN PIMS.PP_PROV_TIN_LOC ptl ON pvl.LOC_ID = ptl.ID INNER JOIN PIMS.PP_PROV_TIN pt ON ptl.TIN_ID = pt.ID LEFT JOIN PIMS.PP_ADDR pa ON ptl.ADDRESS_ID = pa.ID LEFT JOIN PIMS.FMG_ZIPS st ON pa.ZIP = st.ZIP INNER JOIN PIMS.FMG_CITIES ct ON pa.CITY_ID = ct.ID LEFT JOIN PIMS.FMG_COUNTIES counties ON counties.ID = pa.COUNTY_ID LEFT JOIN PET_APP.COUNTY_274_XREF county274Xref1 ON county274Xref1.FMG_COUNTIES_DS = counties.DS WHERE pvl.LOC_ID = '"
								+ rawlist.get(i).getPIMS_PROV_TIN_LOC_ID()
										.toPlainString()
								+ "'  AND pvl.PROV_ID = '"
								+ rawlist.get(i).getPIMS_PROV_ID()
										.toPlainString() + "'");

				List<Object[]> list = (List<Object[]>) addressquery.list();
				if (!list.isEmpty()) {
					if (!((String) list.get(0)[0] == null))
						database.setAddr_line1((String) list.get(0)[0]);
					else
						database.setAddr_line1("");
					if (!(list.get(0)[1] == null))
						database.setAddr_line2((String) list.get(0)[1]);
					else
						database.setAddr_line2("");
					if (!(list.get(0)[2].toString() == null))
						database.setCity(list.get(0)[2].toString());
					else
						database.setCity("");
					if (!((String) list.get(0)[3] == null))
						database.setState_cd((String) list.get(0)[3]);
					else
						database.setState_cd("");
					if (!((String) list.get(0)[4] == null))
						database.setCounty_cd((String) list.get(0)[4]);
					else
						database.setCounty_cd("");

					if (!((String) list.get(0)[5] == null))
						zip = ((String) list.get(0)[5]);
					else {
						zip = "";
					}
					// plus4 for zip
					if (!((String) list.get(0)[6] == null))
						plus = (String) list.get(0)[6];
					else
						plus = "";

					if (parsedbodylist.get(i).getZip_cd().length() == 10)
						database.setZip_cd(zip + "-" + plus);
					if (parsedbodylist.get(i).getZip_cd().length() == 5)
						database.setZip_cd(zip);
				} else {
					database.setAddr_line1("");
					database.setAddr_line2("");
					database.setCity("");
					database.setState_cd("");
					database.setCounty_cd("");
					database.setZip_cd("");
				}

				// Phone Query
				Query phonequery = session
						.createSQLQuery("SELECT  PIMS.PP_PHONES.AREACODE,PIMS.PP_PHONES.EXCHANGE,PIMS.PP_PHONES.NUM,PIMS.PP_PHONES.EXTENSION FROM PIMS.PP_PROV_LOC_CONTACTS INNER JOIN PIMS.PP_CONTACTS ON PIMS.PP_CONTACTS.ID = PIMS.PP_PROV_LOC_CONTACTS.CONTACT_ID INNER JOIN PIMS.PP_CONTACT_PHONES ON PIMS.PP_CONTACTS.ID = PIMS.PP_CONTACT_PHONES.CONTACT_ID INNER JOIN PIMS.PP_PHONES ON PIMS.PP_PHONES.ID = PIMS.PP_CONTACT_PHONES.PHONE_ID WHERE PIMS.PP_CONTACTS.CONTACT_TYPE = 'OM' AND PIMS.PP_PHONES.TYPE = 'PHONE' AND PROV_ID = "
								+ rawlist.get(i).getPIMS_PROV_ID()
								+ " and LOC_ID = "
								+ rawlist.get(i).getPIMS_PROV_TIN_LOC_ID());

				List<Object[]> phoneList = (List<Object[]>) phonequery.list();
				if (!phoneList.isEmpty()) {
					String phone = (String) phoneList.get(0)[0]
							+ (String) phoneList.get(0)[1]
							+ (String) phoneList.get(0)[2];
					database.setPhone((phone));
					if (!(phoneList.get(0)[3] == null))
						database.setPhone_extn((String) phoneList.get(0)[3]);
					else
						database.setPhone_extn("");
					System.out.println("Extension- " + database.getPhone_extn()
							+ "Phone -" + phone);
				} else {
					database.setPhone("");
					database.setPhone_extn("");
				}

				// Fax
				Query faxQuery = session
						.createSQLQuery("SELECT PIMS.PP_PHONES.AREACODE,  PIMS.PP_PHONES.EXCHANGE,  PIMS.PP_PHONES.NUM,  PIMS.PP_PHONES.EXTENSION FROM PIMS.PP_PROV_LOC_CONTACTS INNER JOIN PIMS.PP_CONTACTS ON PIMS.PP_CONTACTS.ID = PIMS.PP_PROV_LOC_CONTACTS.CONTACT_ID INNER JOIN PIMS.PP_CONTACT_PHONES ON PIMS.PP_CONTACTS.ID = PIMS.PP_CONTACT_PHONES.CONTACT_ID INNER JOIN PIMS.PP_PHONES ON PIMS.PP_PHONES.ID                = PIMS.PP_CONTACT_PHONES.PHONE_ID WHERE PIMS.PP_CONTACTS.CONTACT_TYPE = 'OM' AND PIMS.PP_PHONES.TYPE = 'FAX' AND PROV_ID = "
								+ rawlist.get(i).getPIMS_PROV_ID()
								+ " and LOC_ID = "
								+ rawlist.get(i).getPIMS_PROV_TIN_LOC_ID());
				List<Object[]> faxList = (List<Object[]>) faxQuery.list();
				if (!faxList.isEmpty()) {
					String fax = (String) faxList.get(0)[0]
							+ (String) faxList.get(0)[1]
							+ (String) faxList.get(0)[2];
					database.setFax((fax));
				} else {
					database.setFax("");
				}
				// Fed_TaxID
				Query fedTaxquery = session
						.createSQLQuery("Select TIN from PIMS.PP_PROV_TIN where Name = (select NAME from PIMS.PP_PROV where ID ="
								+ rawlist.get(i).getPIMS_PROV_ID() + ")");
				if (!fedTaxquery.list().isEmpty())
					database.setFed_taxid((String) fedTaxquery.list().get(0));
				else
					database.setFed_taxid("");

				// Prov_type
				int specialityId = ((BigDecimal) session
						.createSQLQuery(
								"select SPECIALTY_ID from PIMS.PP_PROV_SPECIALTY where  PRIMARY = 'Y' and PROV_ID ="
										+ rawlist.get(i).getPIMS_PROV_ID())
						.list().get(0)).intValue();
				int arr70[] = { 378, 400 };
				for (int k = 0; k < arr70.length; k++) {
					if (arr70[k] == specialityId) {
						System.out.println("70 working--------");
						database.setProv_type("70");
					}
				}

				int arr16[] = { 80, 102, 103, 104, 105, 106, 107, 108, 109,
						110, 111, 399 };
				for (int k = 0; k < arr16.length; k++) {
					if (arr16[k] == specialityId) {
						System.out.println("16 working--------");
						database.setProv_type("16");
					}
				}
				if (specialityId == 528) {
					System.out.println("60 working--------");
					database.setProv_type("60");
				}

				// Site ID
				Query siteidquery = session
						.createSQLQuery("select LACARE_ID from PFI.PFI_LACARE_XWALK where PIMS_PROV_ID = "
								+ rawlist.get(i).getPIMS_PROV_ID()
								+ "and PIMS_LOC_ID = '"
								+ rawlist.get(i).getPIMS_PROV_TIN_LOC_ID()
								+ "'");
				if (!siteidquery.list().isEmpty()) {
					String str = (String) siteidquery.list().get(0);
					database.setSite_id(str.substring(0, 4));
				} else
					database.setSite_id("");

				// Hosp_Name
				Query hospNameQuery = session
						.createSQLQuery(" Select NAME from PIMS.PP_PROV where ID ="
								+ rawlist.get(i).getPIMS_PROV_ID());
				if (!hospNameQuery.list().isEmpty())
					database.setHosp_nm((String) hospNameQuery.list().get(0));
				else
					database.setHosp_nm("");
				// Medicare Number
				Query medicareNumquery = session
						.createSQLQuery("Select MEDICARENUMBER from LEGACY_RAW.CACT_PROVIDERS where NPI = (select to_char(NUMBER_UDA) from PET_APP.PIMS_UDA_PROV_NPI where (TERM_UDA is null or TERM_UDA >= sysdate) and TYPE_UDA = 'Type 2 Primary' and PROV_ID ="
								+ rawlist.get(i).getPIMS_PROV_ID() + ")");
				if (!medicareNumquery.list().isEmpty()) {
					String medicareNumberDB = ((String) medicareNumquery.list()
							.get(0)).trim();
					String[] str = medicareNumberDB.split("-");
					String medicareNumber = str[0] + str[1];
					database.setMedicare(medicareNumber);
				} else
					database.setMedicare("");

				// NPI
				Query NPIquery = session
						.createSQLQuery("select TO_CHAR(NUMBER_UDA) from (    select subNPI1.PROV_ID, subNPI1.NUMBER_UDA, row_number() OVER (PARTITION BY subNPI1.PROV_ID ORDER BY subNPI1.EFFECTIVE_UDA desc) AS row_num      from PET_APP.PIMS_UDA_PROV_NPI subNPI1     where (subNPI1.TERM_UDA is null or subNPI1.TERM_UDA >= sysdate) and subNPI1.TYPE_UDA = 'Type 2 Subpart' and PROV_ID = "
								+ rawlist.get(i).getPIMS_PROV_ID()
								+ "  )  where row_num = 1");
				if (NPIquery.list().isEmpty()) {
					NPIquery = session
							.createSQLQuery("select TO_CHAR(NUMBER_UDA) from ( select subLocNPI1.PROV_ID, subLocNPI1.LOC_ID, subLocNPI1.NUMBER_UDA, row_number() OVER (partition by subLocNPI1.PROV_ID, subLocNPI1.LOC_ID order by subLocNPI1.EFFECTIVE_UDA desc) row_num from PIMS.V_UDA_BSC_PROV_LOC_NPI subLocNPI1 where (subLocNPI1.TERM_UDA is null or subLocNPI1.TERM_UDA >= sysdate) and subLocNPI1.NPI_TYPE_UDA = 'Type 2 Subpart' and PROV_ID = "
									+ rawlist.get(i).getPIMS_PROV_ID()
									+ " )where row_num = 1");
					if (NPIquery.list().isEmpty()) {
						NPIquery = session
								.createSQLQuery("select TO_CHAR(NUMBER_UDA) from (select primaryNPI1.ID, primaryNPI1.PROV_ID, primaryNPI1.NUMBER_UDA, row_number() OVER (partition by primaryNPI1.PROV_ID order by primaryNPI1.EFFECTIVE_UDA desc) row_num from PET_APP.PIMS_UDA_PROV_NPI primaryNPI1 where (primaryNPI1.TERM_UDA is null or primaryNPI1.TERM_UDA >= sysdate) and primaryNPI1.TYPE_UDA = 'Type 2 Primary' and PROV_ID = "
										+ rawlist.get(i).getPIMS_PROV_ID()
										+ ")where row_num = 1");
						if (NPIquery.list().isEmpty()) {
							NPIquery = session
									.createSQLQuery("select TO_CHAR(NUMBER_UDA) from (select  primaryLocNPI1.PROV_ID, primaryLocNPI1.LOC_ID, primaryLocNPI1.NUMBER_UDA, row_number() OVER (partition by primaryLocNPI1.PROV_ID, primaryLocNPI1.LOC_ID order by primaryLocNPI1.EFFECTIVE_UDA desc) row_num from PIMS.V_UDA_BSC_PROV_LOC_NPI primaryLocNPI1 where (primaryLocNPI1.TERM_UDA is null or primaryLocNPI1.TERM_UDA >= sysdate) and primaryLocNPI1.NPI_TYPE_UDA = 'Type 2 Primary' and PROV_ID = "
											+ rawlist.get(i).getPIMS_PROV_ID()
											+ ") where row_num = 1");
							if (NPIquery.list().isEmpty())
								database.setNpi(null);
						} else
							database.setNpi((String) NPIquery.list().get(0));
					} else
						database.setNpi((String) NPIquery.list().get(0));
				} else
					database.setNpi((String) NPIquery.list().get(0));

				// parent NPI
				Query parentNpiNumquery = session
						.createSQLQuery("select TO_CHAR(NUMBER_UDA) from ( select primaryNPI1.ID, primaryNPI1.PROV_ID, primaryNPI1.NUMBER_UDA, row_number() OVER (partition by primaryNPI1.PROV_ID order by primaryNPI1.EFFECTIVE_UDA desc) row_num  from PET_APP.PIMS_UDA_PROV_NPI primaryNPI1 where (primaryNPI1.TERM_UDA is null or primaryNPI1.TERM_UDA >= sysdate) and primaryNPI1.TYPE_UDA = 'Type 2 Primary' and PROV_ID ="
								+ rawlist.get(i).getPIMS_PROV_ID()
								+ ") where row_num = 1");
				if (parentNpiNumquery.list().isEmpty()) {
					parentNpiNumquery = session
							.createSQLQuery("select TO_CHAR(NUMBER_UDA) from (select  primaryLocNPI1.PROV_ID, primaryLocNPI1.LOC_ID, primaryLocNPI1.NUMBER_UDA, row_number() OVER (partition by primaryLocNPI1.PROV_ID, primaryLocNPI1.LOC_ID order by primaryLocNPI1.EFFECTIVE_UDA desc) row_num from PIMS.V_UDA_BSC_PROV_LOC_NPI primaryLocNPI1 where (primaryLocNPI1.TERM_UDA is null or primaryLocNPI1.TERM_UDA >= sysdate) and primaryLocNPI1.NPI_TYPE_UDA = 'Type 2 Primary' and PROV_ID ="
									+ rawlist.get(i).getPIMS_PROV_ID()
									+ ")where row_num = 1 ");
					if (!parentNpiNumquery.list().isEmpty()) {
						database.setParent_npi((String) parentNpiNumquery
								.list().get(0));
					} else
						database.setParent_npi("");
				} else
					database.setParent_npi((String) parentNpiNumquery.list()
							.get(0));
				if (database.getNpi().equals(database.getParent_npi())) {
					database.setParent_npi("");
				}

				// Taxonomy
				Query taxonomyQuery = session
						.createSQLQuery("select LISTAGG(x.PRPR_TAXONOMY_CD,',') within group(order by ps.PRIMARY desc, x.PRPR_TAXONOMY_CD asc,',') taxonomyAgg from PIMS.PP_PROV_SPECIALTY ps    LEFT JOIN PIMS.PP_SPEC s on s.ID = ps.SPECIALTY_ID    LEFT JOIN PFI.XREF_TYPE_SPEC x on x.SPEC_DS = s.DS  AND x.PRPR_ENTITY = 'F' and x.IS_PRIMARY = (CASE WHEN ps.PRIMARY = 'Y' THEN 1 ELSE 0 END)    WHERE ps.PRIMARY = 'Y' and ps.PROV_ID ="
								+ rawlist.get(i).getPIMS_PROV_ID()
								+ "group by ps.PROV_ID, ps.SPECIALTY_ID");
				if (!taxonomyQuery.list().isEmpty()) {
					String taxonomy = (String) taxonomyQuery.list().get(0);
					List taxArrayList = new ArrayList();
					String[] taxArray = taxonomy.split(",");

					System.out.println(taxArray.length);
					System.out.println(taxArray[0] + taxArray[6]);
					int count = 15 - taxArray.length;
					for (int k = 0; k < taxArray.length; k++) {
						taxArrayList.add(taxArray[k]);
					}
					for (int k = count - 1; k < 15; k++) {
						taxArrayList.add("          ");

					}

					database.setTaxonomy_cd1((String) taxArrayList.get(0));
					database.setTaxonomy_cd2((String) taxArrayList.get(1));
					database.setTaxonomy_cd3((String) taxArrayList.get(2));
					database.setTaxonomy_cd4((String) taxArrayList.get(3));
					database.setTaxonomy_cd5((String) taxArrayList.get(4));
					database.setTaxonomy_cd6((String) taxArrayList.get(5));
					database.setTaxonomy_cd7((String) taxArrayList.get(6));
					database.setTaxonomy_cd8((String) taxArrayList.get(7));
					database.setTaxonomy_cd9((String) taxArrayList.get(8));
					database.setTaxonomy_cd10((String) taxArrayList.get(9));
					database.setTaxonomy_cd11((String) taxArrayList.get(10));
					database.setTaxonomy_cd12((String) taxArrayList.get(11));
					database.setTaxonomy_cd13((String) taxArrayList.get(12));
					database.setTaxonomy_cd14((String) taxArrayList.get(13));
					database.setTaxonomy_cd15((String) taxArrayList.get(14));
					System.out.println("BigDecimal Number:" + taxArray[6]);
				}
				databaselist.add(database);
			}

		}else{
			databaselist=new ArrayList<Database>();
		}
		return databaselist;
		/*
		 * for (HospBody hospBody : databaselist) {
		 * System.out.println(hospBody.getNpi()); }
		 */
	}
}
