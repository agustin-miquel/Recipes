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
                <form class="form-signin col-sm-5 col-md-3" role="form" action="<c:url value='/login' />" method='POST'>

                    <h4 class="form-signin-heading" style="margin-top:15px;">Please sign in:</h4>

                    <label for="username" class="sr-only">User name</label>
                    <input type="text" id="username" name="username" class="form-control" placeholder="User Id" autofocus>
                    <label for="password" class="sr-only">Password</label>
                    <input type="password" id="password" name="password" class="form-control" placeholder="Password">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="remember-me" /> Remember Me:
                        </label>
                    </div>

                    <button class="btn btn-primary btn-block" type="submit">Sign in</button>
                    <button class="btn btn-default btn-block" type="button" onclick="location.href='home'">Cancel"</button>

                    <div id="new_user" style="margin-top:15px;">
                        <a href="new_user">
                            <span class="text text-success"><b>New User</b></span>
                        </a>
                    </div>
                </form>
            </div>

            <div class="row" id="messages">
                <c:if test="${param.error}">
                    <div class="alert alert-danger">
                        Invalid username and password.
                    </div>
                </c:if>

                <c:if test="${param.logout}">
                    <div class="alert alert-warning">
                        You have been logged out.
                    </div>
                </c:if>
            </div>
                
        </div><!-- /container -->
                    
        <jsp:include page="fragments/footer.jsp" />
    </body>
</html>