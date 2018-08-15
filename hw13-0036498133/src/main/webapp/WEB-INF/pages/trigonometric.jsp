<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
	<body bgcolor="${sessionScope.pickedBgColor}">
		<a href="index.jsp">INDEX</a>
		<br></br>
	
		<h1>Trigonometric values</h1>
		<p>Results of computations</p>
		
		<table border="1">
			<thead>
				<tr><th>Value</th><th>Sine</th><th>Cosine</th></tr>
			</thead>
			<tbody>
				<c:forEach var="entry" items="${values}">
					<tr><td>${entry.number}</td><td>${entry.sine}</td><td>${entry.cosine}</td></tr>
				</c:forEach>
			</tbody>
		</table>
	</body>