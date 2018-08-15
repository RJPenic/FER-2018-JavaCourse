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
		<h1>Edit post</h1>
		
		<form action="${pageContext.request.contextPath}/servleti/updateEntry?blogID=${blogID}" method="post">

		<div>
		 <div>
		  <span class="formLabel">Title</span><input type="text" name="title" value='<c:out value="${form.title}"/>' size="100">
		 </div>
		 <c:if test="${form.hasErrorForProperty('title')}">
		 <div class="error"><c:out value="${form.getError('title')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Text</span><input type="text" name="text" value='<c:out value="${form.text}"/>' size="100">
		 </div>
		 <c:if test="${form.hasErrorForProperty('text')}">
		 <div class="error"><c:out value="${form.getError('text')}"/></div>
		 </c:if>
		</div>

		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="method" value="Done">
		  <input type="submit" name="method" value="Cancel">
		</div>
		
		</form>
	</body>
</html>