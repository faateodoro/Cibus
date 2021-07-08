<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../resources/css/estilo.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">
    <title>Cibus - Adicionar</title>
</head>
<body>
     <main>
         <form class="form-adicionar-cozinha" method="post" action="/admin/salvar">
             <label>Nome: <input type="text" name="nome" class="input-nome-cozinha"></label>
             <div class="form-botoes">
                 <input type="submit" class="botao botao-salvar" value="Salvar">
                 <a href="/cozinhas" class="link-cancelar">Cancelar</a>
             </div>
         </form>
     </main>
</body>
</html>