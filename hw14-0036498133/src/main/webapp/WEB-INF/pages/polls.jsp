<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
	<body>
	
		<h1>Trenutno dostupne ankete:</h1>
		<ol>
			<c:forEach var="poll" items="${pollList}">
		 		<li><a href="${pageContext.request.contextPath}/servleti/glasanje?pollID=${poll.id}">${poll.title}</a></li>
			</c:forEach>
		</ol>
		
	</body>