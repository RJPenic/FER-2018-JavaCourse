<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
	<head>
		<style type="text/css">
		table.rez td {text-align: center;}
		</style>
	</head>
	<body>

		<h1>Rezultati glasanja</h1>
		<p>Ovo su rezultati glasanja.</p>
		<table border="1" class="rez">
			<thead><tr><th>Opcija</th><th>Broj glasova</th></tr></thead>
			<tbody>
				<c:forEach var="entry" items="${results}">
					<tr><td>${entry.optionTitle}</td><td>${entry.votesCount}</td></tr>
				</c:forEach>
			</tbody>
		</table>

		<h2>Grafički prikaz rezultata</h2>
		<img alt="Pie-chart" src="glasanje-grafika?pollID=${pollID}" width="400" height="400" />

		<h2>Rezultati u XLS formatu</h2>
		<p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls?pollID=${pollID}">ovdje</a></p>

		<h2>Razno</h2>
		<p>Pobjednici</p>
		<ul>
			<c:forEach var="entry" items="${winners}">
					<li><a href= "${entry.optionLink}" target="_blank">${entry.optionTitle}</a></li>
			</c:forEach>
 		</ul>
 	</body>