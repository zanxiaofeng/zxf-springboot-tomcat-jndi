package com.zxf.springboot.tomcat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@SpringBootApplication
public class SpringApplication {
	@Autowired
	private DataSource myJndiDataSource;


	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
	}

	@PostConstruct
	private void initDatabase() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(myJndiDataSource);
		jdbcTemplate.execute("drop table employees if exists");
		jdbcTemplate.execute("create table employees(id serial, name varchar(255), surname varchar(255))");
		jdbcTemplate.execute("insert into employees(name, surname) values('Jan', 'Kowalski')");
		jdbcTemplate.execute("insert into employees(name, surname) values('Stefan', 'Nowak')");
	}
}
