<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
	<body bgcolor="${sessionScope.pickedBgColor}">
		<a href="index.jsp">INDEX</a>
		<br>
		<br>
		
		<a href="setcolor?color=WHITE">WHITE</a>
		<br>
		<a href="setcolor?color=RED">RED</a>
		<br>
		<a href="setcolor?color=GREEN">GREEN</a>
		<br>
		<a href="setcolor?color=CYAN">CYAN</a>
	</body>