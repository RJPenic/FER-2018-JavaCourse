<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
	<body bgcolor="${sessionScope.pickedBgColor}">
		<a href="index.jsp">INDEX</a>
		<br></br>
		
		<h1>OS usage</h1>
		<p>Here are the results of OS usage in survey that we completed.</p>
		
		<img alt="Pie chart" src="reportImage" height="400" width="400">
		