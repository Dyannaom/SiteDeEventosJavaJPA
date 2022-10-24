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

import br.com.gft.entities.Evento;
import br.com.gft.services.EventoService;


@Controller
@RequestMapping("evento")
public class EventoController {
	
	@Autowired
	private EventoService eventoService;
	
	/**@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private ParticipanteEventoService participanteEventoService;
	
	@Autowired
	private AtividadeService atividadeService;*/
	
	
	@RequestMapping(path = "editar")
	public ModelAndView editarEvento(@RequestParam(required = false) Long id) {
		
		ModelAndView mv = new ModelAndView("evento/form.html");
		
		Evento evento;
		
		if(id==null) {
			evento = new Evento();
		}else {
			try {
				evento = eventoService.obterEvento(id);		
		}catch (Exception e) {
				evento = new Evento();
				mv.addObject("mensagem", e.getMessage());
		}		
	}			
		
		mv.addObject("evento", evento);
		/**mv.addObject("listaDeAtividadesDoEvento", atividadeService.listarAtividade());
		mv.addObject("listaDeGruposDoEvento", listaDeGruposDoEventoService.listarGrupo());*/
		
		return mv;		
	}
	
	
	@RequestMapping(method = RequestMethod.POST, path = "editar")
	public ModelAndView salvarEvento(@Valid Evento evento, BindingResult bindingResult) {
		
		ModelAndView mv = new ModelAndView("evento/form.html");
		
		boolean novo = true;
		
		if(evento.getId()!=null)
			novo = false;
					
		if(bindingResult.hasErrors()) {
			mv.addObject("evento", evento);
			return mv;
		}
					
		eventoService.salvarEvento(evento);				
		
		if (novo) {
			mv.addObject("evento", new Evento());
		}else {
			mv.addObject("evento", evento);
			/**mv.addObject("listaDeAtividadesDoEvento", atividadeService.listarAtividade());*/
			
		}
		
		mv.addObject("mensagem", "Evento salvo com sucesso");	
		
		return mv;
	}
	
	
	
	@RequestMapping
	public ModelAndView listarEvento() {
		
		ModelAndView mv = new ModelAndView("evento/listar.html");
		List<Evento> lista = eventoService.listarEvento();
		mv.addObject("lista", lista);
		if(lista.isEmpty()) {
			mv.addObject("mensagem", "Nenhum evento cadastrado!");
			mv.addObject("cor", "warning");
		}
		
			
		return mv;		
	}
	
	
	
	@RequestMapping("/deletar")
	public ModelAndView excluirEvento(@RequestParam Long id, RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView("redirect:/evento");
					
		try {
			eventoService.excluirEvento(id);	
			redirectAttributes.addFlashAttribute("mensagem", "Evento excluído com sucesso");
		}catch(Exception e) {
			redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir evento!"+e.getMessage());
		}
		
		return mv;		
	}
	
	
	//-----------------------------------------------------------
	


}
