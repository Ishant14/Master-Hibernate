package com.ishant.hibernate.MasterHibernate;

import com.ishant.hibernate.MasterHibernate.jdbc.PersonJdbcDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MasterHibernateApplication implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PersonJdbcDao personJdbcDao;

	public static void main(String[] args) {
		SpringApplication.run(MasterHibernateApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("All users : - {}",personJdbcDao.findAll());
	}
}
