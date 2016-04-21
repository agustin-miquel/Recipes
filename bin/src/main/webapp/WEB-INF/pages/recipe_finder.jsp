<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <jsp:include page="fragments/head.jsp" />
    </head>
    <body onload="setFocusRecipeFinder()">
        <jsp:include page="fragments/header.jsp" />

        <div class="container">
            <h3>
                <span class="label label-info">Recipe Finder</span>
            </h3>

            <div class="row">
                <div class="col-md-6">

                    <!-- FORM1: search criteria -->
                    <form id="form_criteria" class="form-horizontal" action="search_recipes" method="GET" >

                        <div class="form-group">
                            <label for="name" class="control-label col-md-2">Name:</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="name" name="name">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="ingredients" class="control-label col-md-2">Ingredients:</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="ingredients" name="ingredients">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="calories" class="control-label col-md-2">Max.Calories:</label>
                            <div class="col-md-2">
                                <input type="text" class="form-control" id="calories" name="calories">
                            </div>
                        </div>

                        <button type="submit" class="btn btn-success" style="margin-bottom: 10px;">Search</button>
                    </form>
                </div>
            </div>

            <!-- FORM2: go to selected recipe -->
            <form id="form_found_recipes" method="GET" action="detail">

                <input type="hidden" id="selected_recipe" name="selected_recipe">
                <input type="hidden" name="origin" value="recipe_finder">

                <div class="row">
                    <div class="col-md-6">

                        <!-- List of recipes -->
                        <div id="list_recipes">
                            <c:forEach var="recipe" items="${recipes}">
                                <a href="#" onclick="viewRecipeDetail(${recipe.id})" class="list-group-item">
                                    <span class="glyphicon glyphicon-star-empty" aria-hidden="true"></span>
                                    <span class="text-primary"><b>${recipe}</b></span>
                                </a>
                            </c:forEach>
                        </div>

                        <!-- RECIPE DETAIL -->
                        <div class="col-md-6">
                            <p id="recipe_name"/>
                            <p id="recipe_description"/>
                            <p id="recipe_calories"/>
                            <p id="recipe_ingredients"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <input id="finder_back" class="btn btn-default" type="button" value="Back" 
                               onclick="location.href='home'"/>
                        
                        <button id="goto_button" type="submit" class="btn btn-info" style="display:none;" >
                            Go to selected recipe
                        </button>
                    </div>
                </div>
            </form>
                    
        </div><!-- /container -->
                    
        <jsp:include page="fragments/footer.jsp" />
    </body>
</html>
