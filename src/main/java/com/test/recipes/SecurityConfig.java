package com.test.recipes;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * Spring security configuration
 * @author Gines
 */
@Configuration
@EnableWebMvcSecurity
@EnableGlobalAuthentication
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    /**
     * Configure authentication.
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            //.headers()            - with annotations, all security headers are added by default. (but not with XML)
            .authorizeRequests()
                .antMatchers("/resources/**", "/", "/home", "/new_user", "/create_user").permitAll()
                .anyRequest().authenticated()
                .and()
           .formLogin()
                .successHandler(savedRequestAwareAuthenticationSuccessHandler())
                .loginPage("/login")
                .permitAll()                            // this avoids redirect loop
                .failureUrl("/login?error=true")        // Default is "/login?error", that must be checked with: ${param.error != null}, instead of ${param.logout}
                .and()
           .logout()                         
                .permitAll()
                .logoutSuccessUrl("/login?logout=true")   // Idem: if commented out, check with ${param.logout != null}
                .and()
	   .rememberMe()
                .tokenRepository(persistentTokenRepository())
		.tokenValiditySeconds(86400)              // Time the token is valid: one day
                .and()
           .csrf()
                .disable();
    }

    /**
     * Setup authentication strategy.
     *
     * @param auth
     */
    @Autowired  // injection of the method's parameters
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

        // Database:
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select name, password, true from Chef where name=?")
                .authoritiesByUsernameQuery(
                        "select name, 'ROLE_USER' from Chef where name=?");

        // In-memory repository:
//      auth
//             .inMemoryAuthentication()
//             .withUser("user").password("password").roles("USER");
    }

    // REMEMBER-ME functionality ----------------------------------
    
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
        SavedRequestAwareAuthenticationSuccessHandler auth = new SavedRequestAwareAuthenticationSuccessHandler();
        auth.setTargetUrlParameter("targetUrl");
        return auth;
    }
}
