package br.com.alura.cibus.repository;

import br.com.alura.cibus.modelo.Cozinha;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(value = SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class CozinhaRepositoryTest {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	private static final int QUANTIDADE_DE_COZINHAS = 5;
	
	@Test
	public void dadoNomeExistenteNoBancoDeveRetornarCozinha() {
		String nomeCozinhaJaponesa = "Japonesa";
		Optional<Cozinha> cozinhaJaponesa = cozinhaRepository.findByNome(nomeCozinhaJaponesa);
		
		String nomeCozinhaMexicana = "Mexicana";
		Optional<Cozinha> cozinhaMexicana = cozinhaRepository.findByNome(nomeCozinhaMexicana);
		
		assertTrue(cozinhaJaponesa.isPresent());
		assertEquals(nomeCozinhaJaponesa, cozinhaJaponesa.get().getNome());
		assertTrue(cozinhaMexicana.isEmpty());
	}
	
	@Test
	public void deveRetornarListaDeCozinhas() {
		List<Cozinha> listaCozinhas = cozinhaRepository.findByOrderByNome();
		
		char primeiro =  listaCozinhas.get(0).getNome().charAt(0);
		char ultimo = listaCozinhas.get(listaCozinhas.size() - 1).getNome().charAt(0);
		
		assertNotNull(listaCozinhas);
		assertEquals(QUANTIDADE_DE_COZINHAS, listaCozinhas.size());
		assertTrue(primeiro < ultimo);
		assertFalse(ultimo < primeiro);
	}
	
	@Test
	public void deveRetornarVerdadeiroSeACozinhaExistirNoBancoAPartirDoNome() {
		boolean cozinhaJaponesa = cozinhaRepository.existsByNomeIgnoreCase("Japonesa");
		boolean cozinhaArabe = cozinhaRepository.existsByNomeIgnoreCase("Árabe");
		boolean cozinhaTailandesa = cozinhaRepository.existsByNomeIgnoreCase("Tailandesa");
		
		assertTrue(cozinhaJaponesa);
		assertTrue(cozinhaArabe);
		assertFalse(cozinhaTailandesa);
	}
	
	@Test
	public void dadoNomeDeveRetornarVerdadeiroCasoOIdSejaDiferente() {
		boolean cozinhaArabe = cozinhaRepository.existsByNomeIgnoreCaseAndIdNot("Árabe", 42L);
		boolean cozinhaJaponesa = cozinhaRepository.existsByNomeIgnoreCaseAndIdNot("Japonesa", 42L);
		boolean cozinhaJaponesaComDadosCertos = cozinhaRepository.existsByNomeIgnoreCaseAndIdNot("Japonesa", 5L);
		
		assertTrue(cozinhaArabe);
		assertTrue(cozinhaJaponesa);
		assertFalse(cozinhaJaponesaComDadosCertos);
	}

}
