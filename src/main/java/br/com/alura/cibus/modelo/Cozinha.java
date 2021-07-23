package br.com.alura.cibus.modelo;

import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;


@Entity
public class Cozinha {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message="{cozinha.nome.obrigatorio}")
	@Size(max=50, message="{cozinha.nome.maximo}")
	private String nome;

	@Deprecated
	private Cozinha(){}

	public Cozinha(String nome){
		Assert.hasText(nome, "O campo nome é obrigatório!");
		Assert.isTrue(nome.length()<=50, "O campo nome pode ter no máximo 50 caractéres!");
		this.setNome(nome);
	}
	
	public Long getId() {
		return this.id;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Cozinha cozinha = (Cozinha) o;
		return nome.equals(cozinha.nome);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nome);
	}
}
