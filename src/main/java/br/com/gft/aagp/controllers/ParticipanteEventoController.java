package br.com.gft.aagp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Controller;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestMethod;
	import org.springframework.web.bind.annotation.RequestParam;
	import org.springframework.web.servlet.ModelAndView;
	import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.gft.aagp.entities.ParticipanteEvento;
import br.com.gft.aagp.services.ParticipanteEventoService;




	@Controller
	@RequestMapping("participante")
	public class ParticipanteEventoController {
		

		@Autowired
		ParticipanteEventoService participanteService;
		
		
		
		@RequestMapping(method = RequestMethod.GET, path = "editar")
		public ModelAndView cadastrarParticipante() {
			
			
			ModelAndView mv = new ModelAndView("form.html");
			
			mv.addObject("participanteEvento", new ParticipanteEvento());	
			mv.addObject("mensagem","Participante Cadastrado com Sucessso!");
			
			return mv;	
			
		}
		
		
		@RequestMapping(path = "editar")
		public ModelAndView editarParticipante(@RequestParam(required = false) Long id) throws Exception {
			
			ModelAndView mv = new ModelAndView("form.html");		
				
			ParticipanteEvento participante;
			
					if(id==null) {
			participante = new ParticipanteEvento();
		}else {
			try {
				participante = participanteService.obterParticipante(id);
			}catch(Exception e) {
				participante = new ParticipanteEvento();
				mv.addObject("mensagem", e.getMessage());
			}
		}
		
		mv.addObject("participante", participante);
		
		
		return mv;
			
		}

		
		@RequestMapping("/desativar")
		public ModelAndView desativarParticipante(@RequestParam Long id, RedirectAttributes redirectAttributes) {
			
			ModelAndView mv = new ModelAndView("redirect:/index.html");
			
			try {
				participanteService.desativarParticipante(id);
				redirectAttributes.addFlashAttribute("mensagem", "Participante desativado com Sucesso");
			}catch(Exception e){
				redirectAttributes.addFlashAttribute("mensagem", "Erro ao desativar Participante" + e.getMessage());
			}
				
			
			return mv;
		}
		@RequestMapping(path = "lista")
		public ModelAndView listarParticipantesPorNomeOuPorQuatroLetras(String quatroLetras, String nome) {
		
			ModelAndView mv = new ModelAndView("lista.html");
			
			
			mv.addObject("lista", participanteService.listarParticipantesPorQuatroLetrasOuNome(quatroLetras, nome));
					
			return mv;	
		}
		

	}

