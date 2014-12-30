<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <jsp:include page="fragments/head.jsp" />
        
        <!-- Datatable -->
        <link rel="stylesheet" href="//cdn.datatables.net/1.10.4/css/jquery.dataTables.css">
        <link rel="stylesheet" href="//cdn.datatables.net/plug-ins/9dcbecd42ad/integration/bootstrap/3/dataTables.bootstrap.css">
        <link rel="stylesheet" type="text/css" href="resources/css/ingredients_datatable.css">
    </head>
    <body>
        <jsp:include page="fragments/header.jsp" />

        <div class="container">
            <div class="row">
                <h3>
                    <span class="label label-primary">${edit_caption}</span>
                </h3>
            </div>
                
            <div class="row">
                <div class="col-md-6">

                    <!-- FORM: recipe data -->
                    <form id="form_recipe" class="form-horizontal" action="save" method="POST">

                        <input type="hidden" name="id" value="${recipe.id}">
                        <input type="hidden" id="current_ingredient_selected" value="">
                        <input type="hidden" id="new_ingredient_selected_id" value="">
                        <input type="hidden" id="new_ingredient_selected_name" value="">
                        
                        <input type="hidden" id="ingredients_ids" name="ingredients_ids">

                        <!-- SAVE/CANCEL -->
                        <div id="edit_buttons" class="btn-group">
                            <button type="submit" name="action" value="ok" class="btn btn-success" onclick="selectAllIngredients()" >
                                &nbsp;&nbsp; Save &nbsp;&nbsp;
                            </button>
                            <button type="submit" name="action" value="cancel" class="btn btn-default" formnovalidate>
                                Cancel
                            </button>
                        </div>

                        <!-- NAME -->
                        <div class="form-group">
                            <label for="name" class="control-label col-md-2">Name:</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="name" name="name" 
                                       value="${recipe.name}" autofocus>
                            </div>
                        </div>

                        <!-- PREPARATION -->
                        <div class="form-group">
                            <label for="description" class="control-label col-md-2">Preparation:</label>
                            <div class="col-sm-10">
                                <textarea rows="11" class="form-control" id="description" 
                                          name="description">${recipe.description}</textarea>
                            </div>
                        </div>

                        <!-- INGREDIENTS -->
                        <div class="form-group">
                            <label for="ingredients" class="control-label col-md-2">Ingredients:</label>

                            <!-- Current ingredients list -->
                            <div id="current_ingredients_panel" class="col-md-7">
                                <table id="list_current_ingredients" class="table table-striped table-bordered" cellspacing="0" width="100%">
                                    <thead>
                                        <tr>
                                            <th>Id</th>
                                            <th>Name</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <c:forEach var="ingredient" items="${recipe.ingredients}">
                                            <tr>
                                                <td>${ingredient.id}</td>
                                                <td>${ingredient.name}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>            
                            </div>

                            <!-- Buttons to add and remove ingredients -->
                            <div id="buttons_ingredients" class="col-md-3">
                                <div class="btn-group btn-group-sm">
                                    <button id="removeIngredientButton" type="button" class="btn btn-danger" onclick="removeIngredient();" 
                                            style="margin-right: 4px;">
                                        <span class="glyphicon glyphicon-remove"/>
                                    </button>
                                    <button type="button" class="btn btn-success" onclick="addIngredient();">
                                        <span class="glyphicon glyphicon-plus"/>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                                
                <!-- ******************* FINDER for ingredients to add **************** -->
                
                <div id="new_ingredients_panel" class="col-md-6" style="border: 1px solid lightblue; height: 520px;" >

                    <div class="row">

                        <!-- INGREDIENT SELECTION - LEFT COLUMN -->
                        <div id="ingredient_searcher" class="col-md-7">

                            <!-- Name: search criteria for new ingredients -->
                            <div class="row">
                                <div class="col-sm-10">
                                    <label class="text-info ">New Ingredient:</label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-10">
                                    <input class="form-control" type="text" id="search_name">
                                </div>
                            </div>

                            <!-- Search button -->
                            <div class="row">
                                <div class="col-sm-10">
                                    <input type="button" onclick="searchIngredients()" class="btn btn-default" value="Search">
                                </div>
                            </div>

                            <!-- New ingredients list -->
                            <div class="row">
                                <div id="list_new_ingredients" class="col-sm-10">
                                </div>
                            </div>
                        </div>

                        <!-- INGREDIENT DETAIL - RIGHT COLUMN -->
                        <div id="new_ingredient_detail" class="col-md-4">
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div id="messages" class="text-danger">
                    ${message}
                </div>
            </div>
                    
            <div class="row">
                <div id="fatsecretlogo">
                    <a href="http://platform.fatsecret.com">
                        <img src="http://platform.fatsecret.com/api/static/images/fatsecret_platform_120_17.gif" border="0">
                    </a>
                </div>
            </div>
        </div><!-- /container -->
                    
        <jsp:include page="fragments/footer.jsp" />
        
        <!-- =========== DATATABLE ============ -->
        <script src="//cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js"></script>
        <script src="//cdn.datatables.net/plug-ins/9dcbecd42ad/integration/bootstrap/3/dataTables.bootstrap.js"></script>
        <script src="resources/js/ingredients_datatable.js"></script>
    </body>
</html>
