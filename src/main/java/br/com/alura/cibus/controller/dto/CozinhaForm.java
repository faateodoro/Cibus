package br.com.alura.cibus.controller.dto;

import br.com.alura.cibus.modelo.Cozinha;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CozinhaForm {

    @NotBlank(message = "{cozinha.nome.obrigatorio}")
    @Size(max=50, message = "{cozinha.nome.maximo}")
    private String nome;
    
    public CozinhaForm(String nome) {
    	this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public Cozinha toCozinha() {
        return new Cozinha(nome);
    }

}
