package br.com.alura.cibus.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.cibus.modelo.Cozinha;

public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {

	Optional<Cozinha> findByNome(String nome);

	List<Cozinha> findByOrderByNome();
	
	boolean existsByNomeIgnoreCase(String nome);
	
	boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long id);
}
