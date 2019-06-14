package com.bsc.qa.facets.afa.utility;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.bsc.qa.facets.afa.pojo.Connection;

public class HibernateUtil {
	
	
	public static SessionFactory createSessionFactory(Connection conn){
		Configuration configuration = new Configuration().configure();
		configuration.getProperties().setProperty("hibernate.connection.username", conn.getUsername());
		configuration.getProperties().setProperty("hibernate.connection.password", conn.getPassword());
		configuration.getProperties().setProperty("hibernate.connection.url", conn.getUrl());
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
		return factory;
	}
		
}
