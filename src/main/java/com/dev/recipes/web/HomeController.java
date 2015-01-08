package com.dev.recipes.web;

import com.dev.recipes.domain.entities.Recipe;
import com.dev.recipes.service.RecipeServices;
import com.dev.recipes.service.UserServices;
import com.dev.util.ValidationException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        // Access the Business Model: get recent recipes:
        Iterable<Recipe> recipes = recipeServices.getRecentRecipes(MAX_RECIPES);

        // Add attributes to the request for the JSP to use:
        model.addAttribute("recipes", recipes);
        if (message == null || message.isEmpty()) {
            model.addAttribute(startupMessage);
        } else {
            model.addAttribute("message", message);
        }

        // Log information attributes are set in LogInfoFilter:
        model.addAttribute("logged", recipeServices.isChefLogged());
        model.addAttribute("chefname", recipeServices.getChefName());

        // Return the name of the View:
        return "home";
    }
    
    /**
     * Go to the login page
     * @param model
     * @param request
     * @return 
     */
    @RequestMapping("login")
    public String login(Model model, HttpServletRequest request) {
        return "login";
    }
    
    /**
     * Go to new user page
     * @param model
     * @param request
     * @return 
     */
    @RequestMapping("new_user")
    public String newUser(Model model, HttpServletRequest request) {
        return "new_user";
    }
    @RequestMapping("new_user_created")
    public String newUserCreated(Model model, HttpServletRequest request) {
        return "new_user_created";
    }
    
    /**
     * Create new user
     * @param model     
     * @param request
     * @return 
     * 
     * NOTE: RedirectAttributes is a specialization of model: 
     *       Attributes (like error messages) are not lost when doing a redirect.
     */
    @RequestMapping(value="/create_user", method=POST)
    public String createUser(RedirectAttributes model, HttpServletRequest request) {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String password2 = request.getParameter("password2");

            // Check password:
            if (!password.equals(password2)) {
                throw new ValidationException("Password does not match");
            }
            
            // Check if exists:
            if (userServices.exists(username)) {
                throw new ValidationException("User name already exists in the database");
            }
            
            userServices.createUser(username, password);

            // Redirect after POST:
            return "redirect:new_user_created";
        } 
        catch(ValidationException e) {
            model.addFlashAttribute("error", e.getMessage());
            // Redirect after POST:
            return "redirect:new_user";
        }
        catch(Exception e) {
            model.addFlashAttribute("error", "Database error: " + e.getMessage());
            // Redirect after POST:
            return "redirect:new_user";
        }
    }
}
