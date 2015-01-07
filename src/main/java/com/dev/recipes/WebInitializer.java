package com.dev.recipes;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Servlet 3.0 style initialization without web.xml file. On startup, server looks for WebApplicationInitializer.
 * 
 * CAUTION: if the application is built as a JAR,  ServletContextInitializer must be used instead of WebApplicationInitializer.
 */
@Configuration
public class WebInitializer implements ServletContextInitializer { // WebApplicationInitializer {
    
    /**
     * Spring web MVC Initialization.
     * Substitutes traditional XML configuration (since Servlet 3.0)
     * 
     * @param servletContext ServletContext manages communication between the SErvlet and the Container.
     *                      (one context per Web application per JVM.
     * @throws javax.servlet.ServletException
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        // Get the Application Context:
        // (there is no XML (*see below), so use AnnotationConfigWebApplication Context instead of XmlWebApplicationContext)
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        
        // Register and configure DispatcherServlet (the one and only servlet):
        javax.servlet.ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
        dispatcher.addMapping("/");
        dispatcher.setLoadOnStartup(1);

        // Filter to set common logging parameters:
        javax.servlet.FilterRegistration.Dynamic fr = servletContext.addFilter("myFilter", new com.dev.recipes.LogInfoFilter());
        fr.addMappingForUrlPatterns(null, true, "/*");

        // Application is already registered (in its configure method):
        //ctx.register(Application.class);
    }
    
/********************************
XML EQUIVALENT (in web.xml):   

Application.java substitutes:
1. dispatcher-servlet.xml: configuration for Spring MVC
2. dispatcher-config.xml: configuration for persistence, etc.
**********************************/
/*
<!-- DISPATCHER SERVLET -->
<servlet>
   <servlet-name>dispatcher</servlet-name>
   <servlet-class>
     org.springframework.web.servlet.DispatcherServlet
   </servlet-class>
   <init-param>
     <param-name>contextConfigLocation</param-name>
     <param-value>/WEB-INF/spring/dispatcher-config.xml</param-value>
   </init-param>
   <load-on-startup>1</load-on-startup>
 </servlet>
 <servlet-mapping>
   <servlet-name>dispatcher</servlet-name>
   <url-pattern>/</url-patern>
 </servlet-mapping>
    
<!-- CHARACTER ENCODING FILTER -->
<filter>
    <filter-name>encodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter </filter-class>
    <init-param>
        <param-name>encoding</param-name>
        <param-value>UTF-8</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>encodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
    */
}
