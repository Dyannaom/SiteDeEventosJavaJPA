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

import br.com.gft.entities.ParticipanteEvento;
import br.com.gft.services.GrupoService;
import br.com.gft.services.ParticipanteEventoService;




	@Controller
	@RequestMapping("participante")
	public class ParticipanteEventoController {
		

		@Autowired
		ParticipanteEventoService participanteService;
		
		@Autowired
		GrupoService grupoService;
		
			
			
			@RequestMapping(method = RequestMethod.POST, path = "editar")
			public ModelAndView salvarParticipante(@Valid ParticipanteEvento participanteEvento, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
				
				ModelAndView mv;
				
				if(!bindingResult.hasErrors()) {
					mv = new ModelAndView("redirect:/participante");
					participanteService.salvarParticipante(participanteEvento);	
					grupoService.SomarQuantidadeParticipantesNoGrupo(participanteEvento.getGrupo());
					mv.addObject("participanteEvento", participanteEvento);
					redirectAttributes.addFlashAttribute("mensagem", "Participante Cadastrado com Sucesso!");
				} else {
					mv = new ModelAndView("participante/form.html");
					
				}
					
				return mv;
			}
		
		
		@RequestMapping(method = RequestMethod.GET, path = "editar")
		public ModelAndView editarParticipante(@RequestParam(required = false) Long id){
			
			ModelAndView mv = new ModelAndView("participante/form.html");
						
			
			
			if(id==null) {
				mv.addObject("participanteEvento", new ParticipanteEvento());
			}else {
				try {
					mv.addObject("participanteEvento", participanteService.obterParticipante(id));
					
				}catch(Exception e) {
					
					mv.addObject("mensagem", e.getMessage());
			}
		
		}
				
		mv.addObject("listaGrupo", grupoService.listarTodosGrupos());
		
		return mv;
			
		}

		
		@RequestMapping("desativar")
		public ModelAndView desativarParticipante(@RequestParam Long id, RedirectAttributes redirectAttributes) {
			
			ModelAndView mv = new ModelAndView("redirect:/participante");
			
			try {
				participanteService.desativarParticipante(id);
				redirectAttributes.addFlashAttribute("mensagem", "Participante desativado com Sucesso");
			}catch(Exception e){
				redirectAttributes.addFlashAttribute("mensagem", "Erro ao desativar Participante" + e.getMessage());
			}				
			
			return mv;
		}
		@RequestMapping
		public ModelAndView listarParticipantesPorNomeOuPorQuatroLetras(String quatroLetras, String nome) {
		
			ModelAndView mv = new ModelAndView("participante/list.html");
			
			
			mv.addObject("lista", participanteService.listarParticipantesPorQuatroLetrasOuNome(quatroLetras, nome));
					
			return mv;	
		}
		

	}

