package br.com.alura.cibus.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.alura.cibus.controller.dto.CozinhaForm;
import br.com.alura.cibus.exceptions.NotFoundException;
import br.com.alura.cibus.modelo.Cozinha;
import br.com.alura.cibus.repository.CozinhaRepository;

@Controller
public class CozinhaController implements ErrorController {

    private CozinhaRepository cozinhaRepository;

    public CozinhaController(CozinhaRepository cozinhaRepository) {
        this.cozinhaRepository = cozinhaRepository;
    }
    
    @RequestMapping("/error")
    public String handleError() {
    	return "/error/not-found";
    }

    @GetMapping("/cozinhas")
    public String listar(Model model) {
//    	if(result.hasErrors()) {
//    		//throw new 
//    	}
    	
        List<Cozinha> listaCozinhas = this.cozinhaRepository.findByOrderByNome();
        model.addAttribute("listaCozinhas", listaCozinhas);

        return "/listagem";
    }

    @GetMapping("/admin/novo")
    public String adicionar() {
        return "/admin/adicionar";
    }

    @PostMapping("/admin/salvar")
    public String salvar(@Valid CozinhaForm form, BindingResult result, ModelMap model) {
    	if (result.hasFieldErrors("nome")) return "/admin/adicionar";
        
        // TODO: colocar num validator conforme
        Optional<Cozinha> cozinhaJaExiste = this.cozinhaRepository.findByNome(form.getNome());
        
        if (cozinhaJaExiste.isPresent()) {
        	model.put("erro", "Já existe uma cozinha com esse nome.");
        	return "/admin/adicionar";
        }
        
        this.cozinhaRepository.save(form.toCozinha());
        return "redirect:/cozinhas";
    }

    @PostMapping("/admin/salvar/{id}")
    public String salvar(@PathVariable String id, @Valid CozinhaForm form, BindingResult result, ModelMap model) {
    	if(result.hasFieldErrors("nome")) return "redirect:/admin/editar/{id}";
    	
        Optional<Cozinha> cozinhaPorId = this.cozinhaRepository.findById(Long.parseLong(id));
        if(cozinhaPorId.isEmpty()) throw new NotFoundException("Ocorreu um erro. A cozinha não foi encontrada.");
        
        Cozinha cozinha = cozinhaPorId.get();
        
        Optional<Cozinha> nomeJaExiste = this.cozinhaRepository.findByNome(form.getNome());
        if (nomeJaExiste.isEmpty()) {
            cozinha.setNome(form.getNome());
            cozinhaRepository.save(cozinha);
            return "redirect:/cozinhas";
        }
        
        model.put("erro", "Já existe uma cozinha com esse nome.");
    	return "/admin/editar/" + id;
    }

    @PostMapping("/admin/excluir")
    public String excluir(Long id) {
        Optional<Cozinha> cozinha = this.cozinhaRepository.findById(id);
        if (cozinha.isPresent()) this.cozinhaRepository.delete(cozinha.get());

        return "redirect:/cozinhas";
    }

    @GetMapping("/admin/editar/{id}")
    public String editar(@PathVariable String id, Model model) {
    	
        Optional<Cozinha> cozinha = this.cozinhaRepository.findById(Long.parseLong(id));
        if(cozinha.isEmpty()) {
        	throw new NotFoundException("Ocorreu um erro. A cozinha não foi encontrada.");
        }
        
        model.addAttribute("cozinha", cozinha.get());
        return "/admin/editar";
    }
    
    @ExceptionHandler({NotFoundException.class})
    public String handleException(Model model, RuntimeException e) {
    	model.addAttribute("erro", e.getMessage());
    	return "/error/not-found";
    }
}
