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

import br.com.gft.entities.Evento;
import br.com.gft.entities.Grupo;
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

	@RequestMapping(method = RequestMethod.POST, path = "salvar")
	public ModelAndView salvarParticipante(@Valid ParticipanteEvento participanteEvento, BindingResult bindingResult,
			RedirectAttributes ra) {
		ModelAndView mv;

		if (!bindingResult.hasErrors()) {
			Grupo grupo = participanteEvento.getGrupo();
			Evento evento = grupo.getEvento();
			mv = new ModelAndView(
					"redirect:/evento/etapaTresB?idGrupo=" + grupo.getId() + "&" + "idEvento=" + evento.getId());
			participanteService.salvarParticipante(participanteEvento);
			grupoService.SomarQuantidadeParticipantesNoGrupo(grupo);
			ra.addFlashAttribute("mensagem", "Participante Cadastrado com Sucesso!");
			ra.addFlashAttribute("cor", "success");
		} else {
			mv = new ModelAndView("area-acesso-adm/evento/criar-evento/etapa-3/etapa-3-B.html");
		}
		return mv;
	}

	@RequestMapping("desativar")
	public ModelAndView desativarParticipante(@RequestParam Long id, RedirectAttributes redirectAttributes) {

		ModelAndView mv = new ModelAndView("redirect:/participante");

		try {
			participanteService.desativarParticipante(id);
			redirectAttributes.addFlashAttribute("mensagem", "Participante desativado com Sucesso");
		} catch (Exception e) {
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
