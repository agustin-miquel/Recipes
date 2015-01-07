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
            <div class="col-sm-6 col-md-4">
                <h3>
                    <span class="label label-info">${ingredient.name}:</span>
                </h3>

                <div id="ingredient_detail">
                    ${info_ingredient}
                </div>

                <h5>
                    <span class="text text-primary"><b>Recipes with ${ingredient.name}:</b></span>
                </h5>

                <div class="list-group">
                    <c:forEach var="recipe" items="${ingredient.recipes}">
                        <a href="detail?selected_recipe=${recipe.id}&origin=ingredient_detail?ingredient=${ingredient.name}" 
                           class="list-group-item">
                            
                            <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            <span class="text-primary">${recipe}</span>
                        </a>
                    </c:forEach>
                </div>

                <input id="button_back" class="btn btn-default" type="button" value="Back" 
                       onclick="javascript:history.back()"/>
            </div>
        </div><!-- /container -->
                    
        <jsp:include page="fragments/footer.jsp" />
    </body>
</html>
