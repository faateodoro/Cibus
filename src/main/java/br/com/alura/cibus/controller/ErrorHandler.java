package br.com.alura.cibus.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorHandler implements ErrorController {
	
	@RequestMapping("/error")
	public String errorHandler(ModelMap model, Exception e) {
		model.addAttribute("erro", e.getMessage());
		return "error/not-found";
	}
}
