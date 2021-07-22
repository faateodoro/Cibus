<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/resources/css/estilo.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">
    <title>Cibus - Listagem</title>
</head>
<body>
    <main>
        <h1>Tipos de cozinha</h1>
        <a href="/admin/novo" class="botao botao-adicionar">Adicionar</a>
        <table class="tabela-cozinhas">
            <thead>
                <tr>
                    <th class="th-nome">Nome</th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="cozinha" items="${listaCozinhas}">
	                <tr>
	                    <td class="nome-cozinha">${cozinha.nome}</td>
	                    <td><a href="/admin/editar/${cozinha.id}" class="botao botao-editar">Editar</a></td>
	                    <td>
		                    <form action="/admin/excluir" method="post">
		                    	<input type="hidden" value="${cozinha.id}" name="id">
		                    	<input type="submit" class="botao botao-remover" value="Remover">
		                    </form>
						</td>
	                </tr>
                </c:forEach>
            </tbody>
        </table>
    </main>
</body>
</html>