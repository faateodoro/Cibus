<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/resources/css/estilo.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">
<title>Cibus - Não encontrado</title>
</head>
<body>
	<div class="container-erro">
		<h1>Ops, algo deu muito errado!</h1>
		<h2>O recurso que não procurava não foi encontrado.</h2>
		<p class="descricao-erro"><c:out value="${erro}" /></p>
		<a href="/cozinhas" class="botao botao-listagem">Voltar para as cozinhas</a>
	</div>
</body>
</html>