<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <jsp:include page="fragments/head.jsp" />
    </head>
    <body>
        <div class="container">

            <jsp:include page="fragments/header.jsp" />

            <div class="row">
                <form class="form-signin col-sm-5 col-md-3" role="form" action="<c:url value='/create_user' />" method='POST'>

                    <h4 class="form-signin-heading" style="margin-top:15px;">New User:</h4>
                
                    <label for="username" class="sr-only">User name</label>
                    <input type="text" id="username" name="username" class="form-control" placeholder="User Id" autofocus>
                    <label for="password" class="sr-only">Password</label>
                    <input type="password" id="password" name="password" class="form-control" placeholder="Password">
                    <label for="password" class="sr-only">Repeat Password</label>
                    <input type="password" id="password2" name="password2" class="form-control" placeholder="Repeat Password">

                    <button class="btn btn-primary btn-block" style="margin-top: 15px;" type="submit">Create</button>
                    <button class="btn btn-default btn-block"  type="button" onclick="javascript:history.back()" >Cancel</button>
                </form>

                <div class="row" id="messages">
                    <c:if test="${error}">
                        <div class="alert alert-danger">
                            ${error}
                        </div>
                    </c:if>
                </div>
            </div>
                    
        </div><!-- /container -->
                    
        <jsp:include page="fragments/footer.jsp" />
    </body>
</html>