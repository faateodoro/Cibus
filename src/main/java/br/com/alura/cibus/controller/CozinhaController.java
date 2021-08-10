package br.com.alura.cibus.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import br.com.alura.cibus.controller.dto.CozinhaForm;
import br.com.alura.cibus.modelo.Cozinha;
import br.com.alura.cibus.repository.CozinhaRepository;

@Controller
public class CozinhaController {

    private final CozinhaRepository cozinhaRepository;

    public CozinhaController(CozinhaRepository cozinhaRepository) {
        this.cozinhaRepository = cozinhaRepository;
    }
    
    @GetMapping("/cozinhas")
    public String listar(Model model) {
        List<Cozinha> listaCozinhas = this.cozinhaRepository.findByOrderByNome();
        model.addAttribute("listaCozinhas", listaCozinhas);

        return "cozinhas/listagem";
    }

    @GetMapping("/admin/cozinhas/novo")
    public String adicionar() {
        return "/admin/cozinhas/adicionar";
    }

    @PostMapping("/admin/cozinhas/salvar")
    public String salvar(@Valid CozinhaForm form, BindingResult result, Model model) {
    	if (result.hasFieldErrors("nome")) return "admin/cozinhas/adicionar";
        
        if (cozinhaRepository.existsByNomeIgnoreCase(form.getNome())) {
        	model.addAttribute("erro", "Já existe uma cozinha com esse nome.");
        	return "admin/cozinhas/adicionar";
        }
        
        this.cozinhaRepository.save(form.toCozinha());
        return "redirect:/cozinhas";
    }
    
    @GetMapping("/admin/cozinhas/editar/{id}")
    public String editar(@PathVariable String id, Model model) {
    	Cozinha cozinha = this.cozinhaRepository.findById(Long.parseLong(id))
    			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
    					"Ocorreu um erro. A cozinha não foi encontrada"));
    	
    	model.addAttribute("cozinha", cozinha);
    	return "admin/cozinhas/editar";
    }

    @PostMapping("/admin/cozinhas/salvar/{id}")
    public String salvar(@PathVariable("id") String id, @Valid CozinhaForm form, 
    		BindingResult result, Model model) {
    	if(result.hasFieldErrors("nome")) return editar(id, model);
    	
        Cozinha cozinhaPorId = this.cozinhaRepository.findById(Long.parseLong(id))
        		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
        				"Ocorreu um erro. A cozinha não foi encontrada."));
        
        Cozinha cozinha = cozinhaPorId;
        
        if (cozinhaRepository.existsByNomeIgnoreCaseAndIdNot(form.getNome(), 
        		Long.parseLong(id))) {
        	model.addAttribute("erro", "Já existe uma cozinha com esse nome.");
        	return editar(id, model);
        }
        
    	cozinha.setNome(form.getNome());
    	cozinhaRepository.save(cozinha);
    	return "redirect:/cozinhas";
    }

    @PostMapping("/admin/cozinhas/excluir")
    public String excluir(Long id) {
    	this.cozinhaRepository.findById(id).ifPresent(this.cozinhaRepository::delete);

        return "redirect:/cozinhas";
    }
}