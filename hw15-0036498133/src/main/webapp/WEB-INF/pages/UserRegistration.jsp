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
		<title>New user registration</title>
	</head>

	<body>
		<h1>
		New user
		</h1>

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
		
		<form action="save" method="post">

		<div>
		 <div>
		  <span class="formLabel">First name</span><input type="text" name="firstName" value='<c:out value="${form.firstName}"/>' size="50">
		 </div>
		 <c:if test="${form.hasErrorForProperty('firstName')}">
		 <div class="error"><c:out value="${form.getError('firstName')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Last name</span><input type="text" name="lastName" value='<c:out value="${form.lastName}"/>' size="50">
		 </div>
		 <c:if test="${form.hasErrorForProperty('lastName')}">
		 <div class="error"><c:out value="${form.getError('lastName')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">EMail</span><input type="text" name="email" value='<c:out value="${form.email}"/>' size="70">
		 </div>
		 <c:if test="${form.hasErrorForProperty('email')}">
		 <div class="error"><c:out value="${form.getError('email')}"/></div>
		 </c:if>
		</div>
		
		<div>
		 <div>
		  <span class="formLabel">Nick</span><input type="text" name="nick" value='<c:out value="${form.nick}"/>' size="50">
		 </div>
		 <c:if test="${form.hasErrorForProperty('nick')}">
		 <div class="error"><c:out value="${form.getError('nick')}"/></div>
		 </c:if>
		</div>
		
		<div>
		 <div>
		  <span class="formLabel">Password</span><input type="password" name="password" value='<c:out value="${form.password}"/>' size="50">
		 </div>
		 <c:if test="${form.hasErrorForProperty('password')}">
		 <div class="error"><c:out value="${form.getError('password')}"/></div>
		 </c:if>
		</div>

		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="method" value="Register">
		  <input type="submit" name="method" value="Cancel">
		</div>
		
		</form>
	</body>
</html>
