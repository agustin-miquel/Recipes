/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dev.recipes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Local server execution (development)
 * 
 * Execution environments (local/AWS) are set using Spring profiles: 
 *   1. 'local' is defined in application.properties
 *   2. 'amazon' is set in AWS environment configuration, and overrides application.properties:
 *       In the ElasticBeanstalk console, set JVM command line options: -Dspring.profiles.active=amazon
 * 
 * @author Gines Miquel
 */
@Configuration
@Profile("amazon")
public class AmazonServerConfig {

    /**
     * AWS Mysql datasource.
     * @return
     */
    @Bean
    public DriverManagerDataSource dataSource() {

        // CONFIGURATION FOR AMAZON WEB SERVICES:
        String dbName = System.getProperty("RDS_DB_NAME");
        String userName = System.getProperty("RDS_USERNAME");
        String password = System.getProperty("RDS_PASSWORD");
        String hostname = System.getProperty("RDS_HOSTNAME");
        String port = System.getProperty("RDS_PORT");
        String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password + 
                "&characterEncoding=UTF8";
//        System.out.println("**********JDBC URL: " + jdbcUrl);

        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl(jdbcUrl);
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("xxx");

        return driverManagerDataSource;
    }

}
