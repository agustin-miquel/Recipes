<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <jsp:include page="fragments/head.jsp" />
    </head>
    <body>
        <jsp:include page="fragments/header.jsp" />
        
        <div class="container">
            <div class="row">
                <div class="col-sm-6 col-md-4">
                    <a href="list_recipes" class="list-group-item">
                        <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
                        <span class="text-primary"><b>View Recipes</b></span>
                    </a>
                    
                    <c:forEach var="recipe" items="${recipes}">
                        <a href="detail?selected_recipe=${recipe.id}&origin=home" class="list-group-item">
                            <em>&nbsp; &nbsp; &nbsp; Recent recipes:</em>
                            <span class="text-primary">${recipe}</span>
                        </a>
                    </c:forEach>

                    <a href="maintenance_menu" class="list-group-item">
                        <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                        <span class="text-primary"><b>Recipe Maintenance</b></span>
                    </a>

                    <a href="recipe_finder" class="list-group-item">
                        <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                        <span class="text-success"><b>Recipe Finder</b></span>
                    </a>

                    <a href="ingredient_finder" class="list-group-item">
                        <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                        <span class="text-success"><b>Ingredient Finder</b></span>
                    </a>
                </div>
            </div>
            
            <div class="row">
                <div id="messages" class="text-warning col-sm-8">
                    ${message}
                </div>
            </div>
        </div><!-- /container -->
                    
        <jsp:include page="fragments/footer.jsp" />
    </body>
</html>
