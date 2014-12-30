<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="fragments/head.jsp" />
    </head>
    <body>
        <jsp:include page="fragments/header.jsp" />
        
        <div class="container">
            
            <div class="row">
                <div class="col-sm-6 col-md-4">
                    <h4>Your recipes:</h4>
                    <c:forEach var="recipe" items="${recipes}">
                        <a href="detail?selected_recipe=${recipe.name}" class="list-group-item">
                            <span class="glyphicon glyphicon-star-empty" aria-hidden="true"></span>
                            <span class="text-primary">${recipe}</span>
                        </a>
                    </c:forEach>
                </div>
            </div>
            
            <div class="row">
                <div id="messages" class="text-warning col-sm-8">
                    ${message}
                </div>
            </div>

            <input class="btn btn-default" type="button" value="Back" onclick="javascript:history.back()"/>

        </div><!-- /container -->
                    
        <jsp:include page="fragments/footer.jsp" />
    </body>
</html>
