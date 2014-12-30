package com.test.recipes;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Additional configuration
 * @author Gines
 */
@Configuration
public class MvcConfig  extends WebMvcConfigurerAdapter {

    /**
     * Set additional simple controllers.
     * @param registry 
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }
    
    /**
     * Access to resources (css, js...)
     * @param registry 
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/**");
    }
}