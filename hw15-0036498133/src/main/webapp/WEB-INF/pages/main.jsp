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
		
		<h1>Log in</h1>
		
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
		
		<form action="${pageContext.request.contextPath}/servleti/main" method="post">
			<div>
			 <div>
			  <span class="formLabel">Nick</span><input type="text" name="nick" value='<c:out value="${form.nick}"/>' size="50">
			 </div>
			</div>
	
			<div>
			 <div>
			  <span class="formLabel">Password</span><input type="password" name="password" value='<c:out value="${form.password}"/>' size="50">
			 </div>
			 <c:if test="${form.hasError()}">
			 <div class="error"><c:out value="${form.getError()}"/></div>
			 </c:if>
			</div>
			
			<div class="formControls">
		  		<span class="formLabel">&nbsp;</span>
		 		 <input type="submit" value="Submit">
			</div>
		</form>
		
		<br></br>
		
		<a href="${pageContext.request.contextPath}/servleti/register">New user? Register here!</a>
		
		<br></br>
		
		<h1>Users</h1>
		<c:forEach var="entry" items="${userList}">
			<a href="${pageContext.request.contextPath}/servleti/author/${entry}">${entry}</a>
			<br>
		</c:forEach>
	</body>
</html>