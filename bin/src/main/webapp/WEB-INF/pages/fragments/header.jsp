<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
        <div id="header">
            <div class="container">
                <div id="title" class="text-primary">
                    <a href="home">Recipes!</a>
                </div>
                <div id="subtitle" class="text-warning">The new site for your recipes</div>

                <div id="logout">
                    <c:if test="${logged}">
                        Chef: ${chefname} 
                        &nbsp; &nbsp; &nbsp;
                        <a href="<c:url value='/logout' />">Logout</a>
                    </c:if>
                </div>
            </div>
        </div>
