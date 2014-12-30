package com.test.recipes;

import com.test.recipes.service.RecipeServices;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Custom Filter.
 * ->Set log parameters used in all pages.
 * 
 * @author Gines Miquel
 */
public class LogFilter implements Filter {

    private RecipeServices recipeServices;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
  
        request.setAttribute("logged", recipeServices.isChefLogged());
        request.setAttribute("chefname", recipeServices.getChefName());
        
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig cfg) throws ServletException {
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(cfg.getServletContext());
        recipeServices = (RecipeServices)ctx.getBean("recipeServices");
    }

    @Override
    public void destroy() {
    }

}
