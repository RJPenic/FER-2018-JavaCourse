<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
	<body bgcolor="${sessionScope.pickedBgColor}">
		<h1>INDEX</h1>
		<a href="color.jsp">Background color chooser</a>
		
		<br>
		<br>
		
		<b>Calculate sines and cosines(from/to):</b>
		<form action="trigonometric" method="GET">
 			Start angle:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
 			End angle:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
 			<input type="submit" value="Tabulate"><input type="reset" value="Reset">
		</form>
		
		<a href="trigonometric?a=0&b=90">Tabulate for angles between 0 and 90</a>
		
		<br>
		<br>
		
		<a href="funnyfont">Funny story</a>
		
		<br>
		<br>
		
		<a href="report.jsp">OS usage</a>
		
		<br>
		<br>
		
		<a href="powers?a=1&b=100&n=3">Generate powers xml(arguments a=1, b=100, n=3)</a>
		
		<br>
		<br>
		
		<a href="runningTime">How long is this web application running?</a>
	</body>