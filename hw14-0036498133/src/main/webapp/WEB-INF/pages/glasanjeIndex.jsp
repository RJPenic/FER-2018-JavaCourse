<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
	<body>
		<h1>${title}</h1>
	 	<p>${message}</p>
		 <ol>
		 	<c:forEach var="entry" items="${list}">
		 		<li><a href="glasanje-glasaj?id=${entry.id}">${entry.optionTitle}</a></li>
			</c:forEach>
		 </ol>
 </body>