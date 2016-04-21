<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <jsp:include page="fragments/head.jsp" />
    </head>
    <body onload="setFocusIngredientFinder()">
        <jsp:include page="fragments/header.jsp" />
        
        <div class="container">
            <h3 style="margin-bottom: 20px;">
                <span class="label label-info">Ingredient Finder</span>
            </h3>

            <div class="row">
                <div class="col-md-6">

                    <!-- FORM: search criteria -->
                    <form class="form-horizontal" action="search_ingredients" method="GET">

                        <div class="form-group">
                            <label for="name" class="control-label col-md-2">Name:</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="name" name="name">
                            </div>
                        </div>
                        
                        <button type="submit" class="btn btn-success" style="margin-bottom: 10px;">Search</button>
                    </form>

                    <!-- List of ingredients -->
                    <div id="list_ingredients">
                        <c:forEach var="ingredient" items="${ingredients}">
                            <a href="#" onclick="viewIngredientDetail(${ingredient.food_id})" class="list-group-item">
                                <span class="glyphicon glyphicon-star-empty" aria-hidden="true"></span>
                                <span class="text-primary"><b>${ingredient.food_name}</b></span>
                            </a>
                        </c:forEach>
                    </div>
                </div>

                <!-- INGREDIENT DETAIL -->
                <div id="ingredient_detail" class="col-md-6">
                </div>
            </div>

            <div class="row">
                <div id="fatsecretlogo">
                    <a href="http://platform.fatsecret.com">
                        <img src="http://platform.fatsecret.com/api/static/images/fatsecret_platform_120_17.gif" border="0">
                    </a>
                </div>

                <div class="col-md-7">
                    <input id="button_back" class="btn btn-default" type="button" value="Back" style="margin-top: 10px;"
                           onclick="location.href='home'"/>
                </div>
            </div>
        </div><!-- /container -->
                    
        <jsp:include page="fragments/footer.jsp" />
    </body>
</html>
