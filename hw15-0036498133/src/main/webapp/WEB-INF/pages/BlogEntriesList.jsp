<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<header>
		<c:choose>
			<c:when test="${empty sessionScope.id}">
				<label>Not logged in</label>
			</c:when>
			<c:otherwise>
				<label>${sessionScope.fn} ${sessionScope.ln}</label>
				<a href="${pageContext.request.contextPath}/servleti/logout">Log out</a>
			</c:otherwise>
		</c:choose>
		
		<a href="${pageContext.request.contextPath}/servleti/main">HOME</a>
	</header>
	
	<body>
		<h1>
			${nickEnt}'s blog entries
		</h1>
		
		<c:choose>
			<c:when test="${empty entryList}">
				<b>This user hasn't posted any blog posts</b>
			</c:when>
			<c:otherwise>
				<c:forEach var="entry" items="${entryList}">
					<a href="${pageContext.request.contextPath}/servleti/author/${nickEnt}/${entry.id}">${entry.title}</a>
					<br>
				</c:forEach>
			</c:otherwise>
		</c:choose>
		
		<br></br>
		
		<c:if test="${sessionScope.nick eq nickEnt}">
			<a href="${pageContext.request.contextPath}/servleti/author/${nick}/new">Add new blog entry</a>
		</c:if>
	</body>
</html>