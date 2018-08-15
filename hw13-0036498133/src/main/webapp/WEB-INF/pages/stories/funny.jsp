<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
	<body bgcolor="${sessionScope.pickedBgColor}">
		<a href="index.jsp">INDEX</a>
		<br></br>
		<a href="funnyfont">Again</a>
		
		<p>Funny story:</p>
		<font color="${textColor}" face="${textFont}">Java and C were telling jokes. It was C's turn, so he writes something on the wall, points to it and says</font>
		<br>
		<font color="${textColor}" face="${textFont}">"Do you get the reference?" But Java didn't.</font>
	</body>