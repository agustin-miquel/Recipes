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
@Configuration          // indicates that this class defines one or more beans (and is itself a Component)
@Profile("local")       // indicates that this class will run only when Spring profile 'local' is active.
public class LocalServerConfig {

    /**
     * Local Mysql datasource.
     * @return
     */
    @Bean
    public DriverManagerDataSource dataSource() {

        // CAUTION: TEST ONLY (DriverManagerDataSource is for TEST ONLY)
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/recipes?useUnicode=yes&characterEncoding=UTF8");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("root");

        return driverManagerDataSource;
    }

}
