package com.test.recipes;

import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/***********************
 * Spring Configuration
 * *********************
 * 
 * Configuration beans.
 * Additional configuration is located in beans.xml
 * 
 * Spring Boot can be run from a main(), or called from WebInitializer when deployed in Tomcat (see below)
 *
 * Database creation and startup:
 * 1. Create database in MySql: CREATE DATABASE recipes CHARACTER SET utf8 COLLATE utf8_general_ci;
 * 2. Change hibernate properties (below in this class) to 'create'. 
 * 3. Modify RecipeServices so that it calls populateDatabase() (eg: from getUltimasRecetas()).
 * 4. Run the application. 
 * 5. Change back hibernate properties and RecipeServices.
 * 
 * @author Gines Miquel
 */
@Configuration                          // indicates that this class defines one or more beans (and is itself a Component)
@EnableAutoConfiguration                // use reasonable default configurations (based on the classpath)
@ComponentScan                          // search recursively for beans (classes annotated with @Component -> @Controller, @Repository...
@ImportResource("classpath:beans.xml")  // XML additional configuration
@EnableTransactionManagement
public class Application extends SpringBootServletInitializer {
    
    // LOGGING: 'logger' is already defined in Spring (you can just use it):
    // protected org.apache.commons.logging.Log logger = LogFactory.getLog(Application.class);

    // Just wire-up an instance of ApplicationContext:
    // ApplicationContext:
    // - Represents the Container.
    // - Interface to the bean factory.
    // - Maintains a registry of beans.
    @Autowired
    private ApplicationContext context;

    @Autowired
    DataSource dataSource;
    
    // CONFIGURATION BEANS
    
    /**
     * View Resolver
     * @return 
     */
    @Bean   // indicates that a method returns a bean (while @Component annotates a class that is a bean)
    public ViewResolver getViewResolver() {
        
        // LOGGING example:
        logger.info("**** Getting view resolver");
        
        // EXAMPLE: get bean declared in the XML:
        //Novale novale = (Novale)context.getBean("mybean");
        
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/pages/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    /**
     * Hibernate configuration.
     * @return
     */
    Properties hibernateProperties() {
        return new Properties() {
            // (this is just a java initialization block)
            {
                setProperty("hibernate.hbm2ddl.auto", "validate");  // validate | update | create
                setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
            }
        };
    }

    /**
     * Container-managed Entity Manager Factory
     * @return
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setPackagesToScan("com.test.recipes.domain");
        factory.setJpaProperties(hibernateProperties());

        factory.setDataSource(dataSource);

        return factory;
    }

    /**
     * Encoding Filter
     * @return 
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }
    
    /**
     * SpringBootServletInitializer implementation.
     * @param application
     * @return 
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    /**
     * Boot with main() method not used when deploying in Tomcat.
     * @param args
     * @throws Exception 
     */
//    public static void main(String[] args) throws Exception {
//        SpringApplication.run(Application.class, args);
//    }
}
