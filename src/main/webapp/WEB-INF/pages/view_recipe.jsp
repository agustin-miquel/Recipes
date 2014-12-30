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
                    <span class="label label-info">${recipe.name}</span>
                </h3>

                <h4 style="margin-top: 30px;"><b>Preparation:</b></h4>
                <p class="text-primary">
                    ${recipe.description}
                </p>

                <h4><b>Calories:</b></h4>
                <p class="text-primary">
                    ${recipe.calories}
                </p>
                    
                <h4><b>Ingredients:</b></h4>
                <div class="list-group">
                    <c:forEach var="ingredient" items="${recipe.ingredients}">
                        <a href="ingredient_detail?ingredient=${ingredient.name}" class="list-group-item">
                            <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            <span class="text-primary">${ingredient.name}</span>
                        </a>
                    </c:forEach>
                </div>

                <input id="button_back" class="btn btn-default" type="button" value="Back" onclick="location.href='maintenance_menu'"/>
            </div>
        </div><!-- /container -->
                    
        <jsp:include page="fragments/footer.jsp" />
    </body>
</html>
