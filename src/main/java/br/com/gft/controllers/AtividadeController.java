package br.com.gft.controllers;

import java.util.List;

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
	
	@RequestMapping(method = RequestMethod.GET, path = "editar")
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
			mv = new ModelAndView("redirect:/atividade/listar");
			atividadeServices.saveAtividade(atividade);
			ra.addFlashAttribute("mensagem", "Atividade criado com sucesso!");
		}else {
			mv = new ModelAndView("/");
		}
		
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "deletar")
	public ModelAndView deletarAtividade(@RequestParam Long id, RedirectAttributes ra) {
		
		ModelAndView mv = new ModelAndView("redirect:/atividade/listar");
		
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
	
//	@RequestMapping(method = RequestMethod.POST, path = "editar")
//    public ModelAndView salvarAtividade(@Valid Atividade atividade, BindingResult bindingResult) {
//        
//        ModelAndView mv = new ModelAndView("atividade/form.html");
//                    
//        if(bindingResult.hasErrors()) {
//            mv.addObject("atividade", atividade);
//            return mv;
//        }
//        
//        atividadeServices.saveAtividade(atividade);  
//        
//        if(atividade.getId() != null) {
//        	mv.addObject("atividade", new Atividade());
//        } else {        	
//        	mv.addObject("atividade", atividade);      
//        }
//        
//        mv.addObject("mensagem", "Evento salvo com sucesso");    
//        
//        return mv;
//    }
	
	@RequestMapping( method = RequestMethod.GET, path = "listar")
	public ModelAndView listarAtividade() {
		
		ModelAndView mv = new ModelAndView("atividade/list.html");
		
		List<Atividade> listAtividade = atividadeServices.listAtividade();
		mv.addObject("lista", listAtividade);
		if(listAtividade.isEmpty()) {
			mv.addObject("mensagem", "Nenhuma atividade cadastrada!");            
			mv.addObject("cor", "warning");
		}
		
		return mv;
	}
}
