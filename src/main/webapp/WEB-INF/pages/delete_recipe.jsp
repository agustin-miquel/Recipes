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
                    <span class="label label-info">Delete Recipe</span>
                </h3>

                <form class="form-horizontal" action="delete" method="POST">
                    
                    <input type="hidden" name="id" value="${recipe.id}">

                    <div class="text text-primary">
                        ${recipe.name}
                    </div>

                    <div id="buttons_confirm" class="btn-group">
                        <button type="submit" name="action" value="ok" class="btn btn-danger" >Delete</button>
                        <button type="submit" name="action" value="cancel" class="btn btn-default">Cancel</button>
                    </div>
                </form>
                
            </div><!-- /main -->
                    
        </div><!-- /container -->
                    
        <jsp:include page="fragments/footer.jsp" />
    </body>
</html>
