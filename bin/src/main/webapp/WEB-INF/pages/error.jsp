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
                    <span class="text text-danger">ERROR</span>
                </h3>

                <div class="text text-primary">
                    Error: ${error} <br/>
                    Status: ${status} <br/>
                    Message: ${message} <br/>
                    Timestamp: ${timestamp} <br/>
                </div>

                <input id="button_back" class="btn btn-default" type="button" value="Back" 
                       onclick="javascript:history.back()"/>
            </div>
        </div><!-- /container -->
                    
        <jsp:include page="fragments/footer.jsp" />
    </body>
</html>
