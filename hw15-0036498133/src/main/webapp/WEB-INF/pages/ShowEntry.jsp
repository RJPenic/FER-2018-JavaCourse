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
	
	<head>
			<style type="text/css">
			.error {
			   font-family: fantasy;
			   font-weight: bold;
			   font-size: 0.9em;
			   color: #FF0000;
			   padding-left: 110px;
			}
			.formLabel {
			   display: inline-block;
			   width: 100px;
	                   font-weight: bold;
			   text-align: right;
	                   padding-right: 10px;
			}
			.formControls {
			  margin-top: 10px;
			}
			</style>
	</head>
	
	<body>
		<h2>${blogEntry.title}</h2>
		<b>${blogEntry.text}</b>
		
		<br></br>
		
		<c:if test="${sessionScope.nick eq nickEnt}">
			<a href="${pageContext.request.contextPath}/servleti/author/${nick}/edit?blogID=${blogEntry.id}">Edit this blog entry</a>
		</c:if>
		
		<br></br>
		
		<h1>Comments:</h1>
		
		<c:choose>
			<c:when test="${empty commentList}">
				<b>There are no comments on this post</b>
			</c:when>
			<c:otherwise>
				<c:forEach var="comment" items="${commentList}">
					<b>----${comment.usersEMail}----</b>
					<br>
					<b>${comment.message}</b>
					<br>
					<b>-----------------------------</b>
					<br>
				</c:forEach>
			</c:otherwise>
		</c:choose>
		
		<br></br>
		
		<h1>Leave a comment:</h1>
		
		<form action="${pageContext.request.contextPath}/servleti/saveComment?nick=${nickEnt}&blogID=${blogEntry.id}" method="post">
				<c:if test="${empty sessionScope.id}">
					<div>
						<div>
				  			<span class="formLabel">EMail</span><input type="text" name="email" value='<c:out value="${form.email}"/>' size="70">
				 		</div>
				 		<c:if test="${form.hasErrorForProperty('email')}">
						 <div class="error"><c:out value="${form.getError('email')}"/></div>
						 </c:if>
					</div>
				</c:if>
		
				<div>
					<div>
						<span class="formLabel">Message</span><input type="text" name="message" value='<c:out value="${form.message}"/>' size="100">
					</div>
				 	<c:if test="${form.hasErrorForProperty('message')}">
						 <div class="error"><c:out value="${form.getError('message')}"/></div>
						 </c:if>
				</div>
				
				<div class="formControls">
			  		<span class="formLabel">&nbsp;</span>
			 		<input type="submit" value="Submit">
				</div>
		</form>
	</body>
</html>