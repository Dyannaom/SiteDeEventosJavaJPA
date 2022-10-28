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
	
	/*
	@RequestMapping(method = RequestMethod.GET, path = "/editar")
	public ModelAndView editarGrupo(@RequestParam(required = false) Long id) {
		ModelAndView mv = new ModelAndView("grupo/formGrupo.html");
		if (id == null) {
			mv.addObject("grupo", new Grupo());
		} else {
			try {
				mv.addObject("grupo", grupoService.obterGrupo(id));
			} catch (Exception e) {
				mv.addObject("mensagem", e.getMessage());
			}
		}
		mv.addObject("listaParticipantes", participanteEventoService.listarParticipantesDoEvento());
		return mv;
	}
	*/

	/*@RequestMapping(method = RequestMethod.POST, path = "salvar")
	public ModelAndView salvarGrupo(@Valid Grupo grupo, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		ModelAndView mv;
		if (!bindingResult.hasErrors()) {
			mv = new ModelAndView("redirect:/grupo");
			grupoService.salvarGrupo(grupo);
			mv.addObject("grupo", grupo);
			redirectAttributes.addFlashAttribute("mensagem", "Grupo salvo com sucesso!");
		} else {
			mv = new ModelAndView("grupo/formGrupo.html");
		}
		return mv;
	}*/
	
	@RequestMapping(method = RequestMethod.POST, path = "salvar")
	public ModelAndView salvarGrupo(@Valid Grupo grupo, BindingResult bindingResult,
			RedirectAttributes ra) {
		ModelAndView mv;
		if (!bindingResult.hasErrors()) {
			grupoService.salvarGrupo(grupo);
			mv = new ModelAndView("redirect:/evento/etapaTresA?&id=" + grupo.getEvento().getId());
			mv.addObject("grupo", grupo);
			ra.addFlashAttribute("mensagem", "Grupo salvo com sucesso!");
			ra.addFlashAttribute("cor", "success");
		} else {
			mv = new ModelAndView("area-acesso-adm/evento/criar-evento/etapa-2/etapa-2-B.html");
			ra.addAttribute("grupo", new Grupo());
		}
		return mv;
	}

	@RequestMapping(method = RequestMethod.GET, path = "")
	public ModelAndView listarGrupos() {
		ModelAndView mv = new ModelAndView("/grupo/listGrupo.html");
		try {
			mv.addObject("listaGrupo", grupoService.listarTodosGrupos());
		} catch (Exception e) {
			mv.addObject("mensagem", e.getMessage());
		}
		mv.addObject("listaParticipantes", participanteEventoService.listarParticipantesDoEvento());
		mv.addObject("quantidadeGrupo", grupoService.listarTodosGrupos().size());
		return mv;
	}

	@RequestMapping(method = RequestMethod.GET, path = "excluir")
	public ModelAndView excluirGrupo(@RequestParam Long id, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("redirect:/grupo");
		try {
			grupoService.excluirGrupo(id);
		} catch (Exception e) {
			mv.addObject("mensagem", e.getMessage());
		}
		return mv;
	}
}
