package com.bsc.qa.facets.afa.test;

import com.bsc.qa.facets.afa.dao.QueriesUtil;

public class Testy {
public static void main(String[] args) {
	QueriesUtil util = new QueriesUtil();
	System.out.println(util.queriesMap().get("ErrorDescriptionQuery"));
}
}
