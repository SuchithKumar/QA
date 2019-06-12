package com.bsc.qa.lacare.util;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.bsc.qa.lacare.pojo.Connection;

public class HibernateUtil {
	public static SessionFactory createSessionFactory(Connection conn){
		Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
		configuration.getProperties().setProperty("hibernate.connection.username", conn.getUsername());
		configuration.getProperties().setProperty("hibernate.connection.password", conn.getPassword());
		configuration.getProperties().setProperty("hibernate.connection.url", conn.getUrl());

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
		return factory;
	}
	
	public static boolean testConnection(Connection conn){
		boolean success = false;
		Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
		configuration.getProperties().setProperty("hibernate.connection.username", conn.getUsername());
		configuration.getProperties().setProperty("hibernate.connection.password", conn.getPassword());
		configuration.getProperties().setProperty("hibernate.connection.url", conn.getUrl());
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
		Session session = factory.openSession();
		Transaction txn = session.beginTransaction();
		success = session.isConnected();
		txn.commit();
		session.close();
		return success;
	}
	
}
