package br.com.alura.cibus.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import br.com.alura.cibus.modelo.Cozinha;
import br.com.alura.cibus.repository.CozinhaRepository;

@Controller
public class CozinhaController {
	
	private CozinhaRepository cozinhaRepository;
	
	public CozinhaController(CozinhaRepository cozinhaRepository) {
		this.cozinhaRepository = cozinhaRepository;
	}
	
    @GetMapping("/cozinhas")
    public String listar(Model model){
    	List<Cozinha> listaCozinhas = this.cozinhaRepository.findByOrderByNome();
    	model.addAttribute("listaCozinhas", listaCozinhas);
    	
        return "/listagem";
    }
    
    @GetMapping("/admin/novo")
    public String adicionar() {
    	return "/admin/adicionar";
    }
    
    @PostMapping("/admin/salvar")
    public RedirectView salvar(@Valid String nome) {
    	Optional<Cozinha> cozinhaJaExiste = this.cozinhaRepository.findByNome(nome);
    	if(cozinhaJaExiste.isEmpty())
    		this.cozinhaRepository.save(new Cozinha(nome));
    	
    	return new RedirectView("/cozinhas");
    }
    
    @PostMapping("/admin/salvar/{id}")
    public RedirectView salvar(@PathVariable String id, @Valid String nome) {
    	Cozinha cozinha = this.cozinhaRepository.findById(Long.parseLong(id)).get();
    	Optional<Cozinha> nomeJaExiste = this.cozinhaRepository.findByNome(nome);
    	if(nomeJaExiste.isEmpty()) {
    		cozinha.setNome(nome);
    		cozinhaRepository.save(cozinha);
    	}
    		
    	return new RedirectView("/cozinhas");
    }
    
    @PostMapping("/admin/excluir")
    public RedirectView excluir(Long id) {
    	Optional<Cozinha> cozinha = this.cozinhaRepository.findById(id);
    	if(cozinha.isPresent())
    		this.cozinhaRepository.delete(cozinha.get());
    	
    	return new RedirectView("/cozinhas");
    }
    
    @GetMapping("/admin/editar/{id}")
    public String editar(@PathVariable String id, Model model) {
    	Optional<Cozinha> cozinha = this.cozinhaRepository.findById(Long.parseLong(id));
    	model.addAttribute("cozinha", cozinha.get());
    	return "/admin/editar";
    }
}
