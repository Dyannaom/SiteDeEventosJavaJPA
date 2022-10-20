package br.com.gft.aagp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.gft.aagp.entities.Grupo;
import br.com.gft.aagp.services.GrupoService;
import br.com.gft.aagp.services.ParticipanteEventoService;


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
		
		Grupo grupo;
		
		if(id==null) {
			grupo = new Grupo();
		}else {
			try {
				grupo = grupoService.obterGrupo(id);
			}catch(Exception e) {
				grupo = new Grupo ();
				mv.addObject("mensagem", e.getMessage());
			}
		}
		
		mv.addObject("grupo", grupo);
		mv.addObject("listaParticipantes", participanteEventoService.listarParticipantesDoEvento());
		
		return mv;
		
}
	
	@RequestMapping(method = RequestMethod.POST, path = "editar")
	public ModelAndView salvarGrupo(@Valid Grupo grupo, BindingResult bindingResult) {
		
		ModelAndView mv = new ModelAndView("grupo/form.html");
		
		boolean novo = true;
		
		if(grupo.getId() != null) {
			novo = false;
		}
		
		if(bindingResult.hasErrors()) {
			mv.addObject("grupo", grupo);
			return mv;
		}
		
		grupoService.salvarGrupo(grupo);
		
		if(novo) {
			mv.addObject("grupo", new Grupo());
		} else {
			mv.addObject("grupo", grupo);
		}
		
		mv.addObject("mensagem", "Grupo salvo com sucesso");
		mv.addObject("listaParticipantes", participanteEventoService.listarParticipantesDoEvento());
		
		return mv;
	}
	
	
	@RequestMapping
	public ModelAndView listarGrupos(String nome, int quantidadeDePessoas) {
		
		ModelAndView mv = new ModelAndView("grupo/listar.html");
		
		mv.addObject("listaGrupo", grupoService.listarGrupos(nome, quantidadeDePessoas));
		mv.addObject("nome", nome);
		mv.addObject("quantidadeDePessoas", quantidadeDePessoas);
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
