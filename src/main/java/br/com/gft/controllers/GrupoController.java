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

import br.com.gft.entities.Grupo;
import br.com.gft.services.GrupoService;
import br.com.gft.services.ParticipanteEventoService;


@Controller
@RequestMapping("grupo")
public class GrupoController {
	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private ParticipanteEventoService participanteEventoService;
	
	@RequestMapping(method = RequestMethod.GET, path = "/editar")
	public ModelAndView editarGrupo(@RequestParam(required = false) Long id) {
		
		ModelAndView mv = new ModelAndView("grupo/form.html");
		
				
		if(id==null) {
			mv.addObject("grupo", new Grupo());
		}else {
			try {
				mv.addObject("grupo", grupoService.obterGrupo(id));
			}catch(Exception e) {
				mv.addObject("grupo", new Grupo());
				mv.addObject("mensagem", e.getMessage());
			}
		}
		
		mv.addObject("listaParticipantes", participanteEventoService.listarParticipantesDoEvento());
		
		return mv;
		
}
	
	@RequestMapping(method = RequestMethod.POST, path = "editar")
	public ModelAndView salvarGrupo(@Valid Grupo grupo, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		ModelAndView mv;
		
		if(!bindingResult.hasErrors()) {
			mv = new ModelAndView("redirect:/grupo");
			grupoService.salvarGrupo(grupo);		
			mv.addObject("grupo", grupo);
		} else {
			mv = new ModelAndView("grupo/form.html");
		}
			
		return mv;
	}
	
	
	@RequestMapping
	public ModelAndView listarGrupos() {
		
		ModelAndView mv = new ModelAndView("grupo/list.html");
		
		mv.addObject("listaGrupo", grupoService.listarTodosGrupos());
		mv.addObject("listaParticipantes", participanteEventoService.listarParticipantesDoEvento());
		
		return mv;
	}
	
	@RequestMapping("/desativar")
	public ModelAndView desativarGrupo(@RequestParam Long id, RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView("redirect:/grupo");
		
		try {
			grupoService.desativarGrupo(id);
			redirectAttributes.addFlashAttribute("mensagem", "Grupo desativado com sucesso.");
		}catch(Exception e) {
			redirectAttributes.addFlashAttribute("mensagem", "Erro ao desativar grupo!"+e.getMessage());
		}
		
		return mv;
	}
		
}
