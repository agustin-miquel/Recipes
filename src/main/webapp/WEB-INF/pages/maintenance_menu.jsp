<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <jsp:include page="fragments/head.jsp" />
        
        <!-- Datatable -->
        <link rel="stylesheet" href="//cdn.datatables.net/1.10.4/css/jquery.dataTables.css">
        <link rel="stylesheet" href="//cdn.datatables.net/plug-ins/9dcbecd42ad/integration/bootstrap/3/dataTables.bootstrap.css">
        <link rel="stylesheet" type="text/css" href="resources/css/recipes_datatable.css">
    </head>
    <body>

        <jsp:include page="fragments/header.jsp" />

        <div class="container">
            <div class="col-sm-8 col-md-6">
                <h4>Your Recipes:</h4>

                <form id="form_maintenance" class="form-horizontal" action="maintenance" method="GET">

                    <input type="hidden" id="selected_recipe" name="selected_recipe">

                    <table id="recipelist" class="table table-striped table-bordered" cellspacing="0" width="100%">
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Name</th>
                            </tr>
                        </thead>

                        <tbody>
                            <c:forEach var="recipe" items="${recipes}">
                                <tr>
                                    <td>${recipe.id}</td>
                                    <td>${recipe.name}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>            

                    <div id="menu_buttons">
                        <div class="btn-group">
                            <button name="action" value="create" type="submit" class="btn btn-primary">
                                New Recipe
                            </button>
                            <button name="action" value="view" type="submit" class="btn btn-success">
                                View
                            </button>
                            <button name="action" value="edit" type="submit" class="btn btn-warning">
                                Edit
                            </button>
                            <button name="action" value="delete" type="submit" class="btn btn-danger">
                                Delete
                            </button>
                        </div>

                        <input id="back" class="btn btn-default" type="button" value="Back" onclick="location.href='home'" />
                    </div>
                </form>

                <div id="messages" class="text-danger">
                    ${message}
                </div>
                
            </div><!-- /main -->
                    
        </div><!-- /container -->
                    
        <jsp:include page="fragments/footer.jsp" />
        
        <!-- =========== DATATABLE ============ -->
        <script src="//cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js"></script>
        <script src="//cdn.datatables.net/plug-ins/9dcbecd42ad/integration/bootstrap/3/dataTables.bootstrap.js"></script>
        <script src="resources/js/recipes_datatable.js"></script>
    </body>
</html>
