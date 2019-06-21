package com.bsc.qa.facets.bor_file_generator_stt.util;

import java.util.List;
import java.util.Random;

import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

public class DatabaseValidationApp {

	public static String generateUniqueClaimNumber(Session session) {
		String claimNumber = new String();
		Random r = new Random();
		Integer za = 100000 + r.nextInt(900000);
		String zas = za.toString();
		Integer zb = 10000 + r.nextInt(90000);
		String zbs = zb.toString();
		claimNumber = "R" + zas + zbs;

		while (DatabaseValidationApp.checkClaimID(session, claimNumber) == false) {
			claimNumber = DatabaseValidationApp.generateUniqueClaimNumber(session);
		}
		return claimNumber;

	}

	public static boolean checkClaimID(Session session, String claimId) {
		SQLQuery query = session
				.createSQLQuery("Select * from FACETS_CUSTOM.AFA_RECONNET_TRNS_HIST where CLM_ID ='"
						+ claimId + "'");
		List results = query.list();

		if (results.isEmpty()) {
			return true;
		}
		return false;
	}

	public static String generateCheckNumber(Session session) {
		Random r = new Random();
		Integer zb = 1000000 + r.nextInt(9000000);
		String zbs = zb.toString();

		while (DatabaseValidationApp.check(session, zbs) == false)
			zbs = DatabaseValidationApp.generateCheckNumber(session);
		return zbs;
	}

	public static boolean check(Session session, String checkNo) {
		SQLQuery query = session
				.createSQLQuery("SELECT BPID_CK_NO FROM FACETS.CMC_BPID_INDIC WHERE BPID_CK_NO='"
						+ checkNo + "'");
		List results = query.list();
		if (results.isEmpty()) {
			return true;
		}
		return false;

	}

}
