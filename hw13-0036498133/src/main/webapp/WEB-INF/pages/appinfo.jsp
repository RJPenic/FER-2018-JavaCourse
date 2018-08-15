<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
	<body bgcolor="${sessionScope.pickedBgColor}">
		<a href="index.jsp">INDEX</a>
		<br>
		<br>
		
		<b>Application has been running for:</b><br>
		<b>${runningTime}</b>
	</body>