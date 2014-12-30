package com.test.recipes.web;

import com.test.recipes.domain.entities.Recipe;
import com.test.recipes.service.RecipeServices;
import com.test.recipes.service.UserServices;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Home page Controller.
 */
@Controller
public class HomeController {

    private static final int MAX_RECIPES = 2;

    // Get a value from src/main/resources/application.properties:
    @Value("${application.message:(\'message\' not defined in application.properties)}")
    private String startupMessage;

    @Autowired
    RecipeServices recipeServices;

    @Autowired
    UserServices userServices;

    /**
     * Request handling method of this MVC Controller Mapped to requests: '/' and '/home'
     *
     * The signature of a request handling method can include many parameters: The Model map, HttpServletRequest and Response, HTTP headers,
     * Query parameters, Cookie values...
     *
     * Returns the logical name of the View that will render the Model. DispatcherServlet will use this name to get the actual view
     * implementation from the View Resolver.
     *
     * @param model - The MVC Model
     * @param request
     * @return The MVC View
     */
    @RequestMapping({"/","home"})
    public String homePage(Model model, HttpServletRequest request) {
        
        String message = request.getParameter("message");

        // Access the Business Model:
        Iterable<Recipe> recipes = recipeServices.getRecentRecipes(MAX_RECIPES);

        // Add attributes to the request for the JSP to use:
        model.addAttribute("recipes", recipes);
        if (message == null || message.isEmpty()) {
            model.addAttribute(startupMessage);
        } else {
            model.addAttribute("message", message);
        }

        // Log attributes are set in LogFilter:
//        model.put("logged", recipeServices.isChefLogged());
//        model.put("chefname", recipeServices.getChefName());

        // Return the name of the View:
        return "home";
    }
    
    /**
     * Go to new user page
     * @param model
     * @param request
     * @return 
     */
    @RequestMapping("new_user")
    public String newUser(Model model, HttpServletRequest request) {
        model.addAttribute("message", request.getAttribute("message"));
        return "new_user";
    }
    
    /**
     * Create new user
     * @param model
     * @param request
     * @return 
     */
    @RequestMapping(value="/create_user", method=POST)
    public String createUser(Model model, HttpServletRequest request) {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String password2 = request.getParameter("password2");

            if (!password.equals(password2)) {
                throw new Exception("Password does not match");
            }
            
            userServices.createUser(username, password);

            model.addAttribute("message", "Created user " + username);

            // Redirect after POST:
            return "redirect:home";
        } 
        catch(Exception e) {
            model.addAttribute("message", e.getMessage());

            // Redirect after POST:
            return "redirect:new_user";
        }
    }
}
