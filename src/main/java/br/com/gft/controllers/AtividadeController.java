package br.com.gft.controllers;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.gft.entities.Atividade;
import br.com.gft.services.AtividadeServices;

@Controller
@RequestMapping ("atividade")
public class AtividadeController {
	
	@Autowired
	AtividadeServices atividadeServices;
	
	@RequestMapping(method = RequestMethod.GET, path = "/")
	public ModelAndView editarAtividade(@RequestParam(required = false) Long id) {
		
		ModelAndView mv = new ModelAndView("atividade/form.html");
		if(id == null) {
			mv.addObject("atividade", new Atividade());			
		}else {
			try {
				mv.addObject("atividade", atividadeServices.getAtividade(id));
			}catch(Exception e) {
				mv.addObject("message", e.getMessage());
			}
		}
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "salvar")
	public ModelAndView salvarAtividade(@Valid Atividade atividade, RedirectAttributes ra, BindingResult bindingResult) {
		
		ModelAndView mv;
				
		if(!bindingResult.hasErrors()) {
			mv = new ModelAndView("redirect:/atividade/get");
			atividadeServices.saveAtividade(atividade);
			ra.addFlashAttribute("mensagem", "Atividade criado com sucesso!");
		}else {
			mv = new ModelAndView("/");
		}
		
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "deletar")
	public ModelAndView deletarAtividade(@RequestParam Long id, RedirectAttributes ra) {
		
		ModelAndView mv = new ModelAndView("redirect:/atividade/get");
		
		try {
			atividadeServices.deleteAtividade(id);
			ra.addFlashAttribute("message", "Atividade excluida com sucesso!");
			
		}catch(Exception e){
			ra.addFlashAttribute("message" , "Erro ao excluir atividade" + e.getMessage());
		}
		
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "get")
	public ModelAndView getAtividade(@RequestParam Long id) {
		
		ModelAndView mv = new ModelAndView("atividade/mostrar-atividade.html");
		
		try {
			
			mv.addObject("atividade", atividadeServices.getAtividade(id));
			
		}catch(Exception e){
			mv.addObject("message", e.getMessage());
		}
		
		return mv;
		
	}
}
