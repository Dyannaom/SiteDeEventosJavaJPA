package br.com.gft.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.gft.entities.Atividade;
import br.com.gft.entities.DiaDeEvento;
import br.com.gft.entities.Evento;
import br.com.gft.entities.Grupo;
import br.com.gft.entities.ParticipanteEvento;
import br.com.gft.services.DiaDeEventoService;
import br.com.gft.services.EventoService;
import br.com.gft.services.GrupoService;

@Controller
@RequestMapping("/evento")
public class EventoController {

	@Autowired
	private EventoService eventoService;
	@Autowired
	private DiaDeEventoService diaDeEventoService;
	@Autowired
	private GrupoService grupoService;
	

	@RequestMapping
	public ModelAndView listarEventos() {
		
		ModelAndView mv = new ModelAndView("area-acesso-adm/evento/listar-eventos.html");
		List<Evento> lista = eventoService.listarEvento();
		mv.addObject("lista", lista);
		if (lista.isEmpty()) {
			mv.addObject("mensagem", "Nenhum evento cadastrado!");
			mv.addObject("cor", "warning");
		}
		
		return mv;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.GET, path = "/etapaUm")
	public ModelAndView criarEvento(@RequestParam(required = false) Long id) {
		
		ModelAndView mv = new ModelAndView("area-acesso-adm/evento/criar-evento/etapa-1/etapa-1.html");
		mv.addObject("evento", new Evento());
		return mv;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.POST, path = "/salvar")
	public ModelAndView salvarEvento(@Valid Evento evento, BindingResult bindingResult, RedirectAttributes ra) {

		ModelAndView mv;
		LocalDate dataInicio = evento.getDataInicio();
		LocalDate dataFinal = evento.getDataFinal();
		boolean datasEstaoCoerentes = true;
		
		if(!eventoService.primeiraDataEAntesDaSegundaData(dataInicio, dataFinal) && !dataInicio.isEqual(dataFinal))
			datasEstaoCoerentes = false;

		if (!bindingResult.hasErrors() && datasEstaoCoerentes) {
			evento = eventoService.salvarEvento(evento);
			eventoService.criarListaDeDiasDeEvento(evento);
			mv = new ModelAndView("redirect:/evento/etapaDoisA?&id=" + evento.getId());
			
		} else {
			mv = new ModelAndView("area-acesso-adm/evento/criar-evento/etapa-1/etapa-1.html");
			if(!datasEstaoCoerentes) {
				mv.addObject("evento", evento);
				mv.addObject("mensagem", "Data de Final está antes da data de inicio, Por favor insira as datas corretamente!");
				mv.addObject("cor", "danger");
			}
		}

		return mv;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.GET, path = "/etapaDoisA")
	public ModelAndView listarDiasDeEvento(@RequestParam Long id) {
		
		ModelAndView mv = new ModelAndView("area-acesso-adm/evento/criar-evento/etapa-2/etapa-2-A.html");
		try {
			Evento evento = eventoService.obterEvento(id);
			List<DiaDeEvento> lista = diaDeEventoService.listarDiasPorEvento(evento);
			mv.addObject("lista", lista);
			mv.addObject("evento", evento);
			if (lista.isEmpty()) {
				mv.addObject("mensagem", "Nenhum dia cadastrado nesse Evento!");
				mv.addObject("cor", "warning");
			}
		} catch (Exception e) {
			mv.addObject("mensagem", e.getMessage());
			mv.addObject("cor", "danger");
		}
		return mv;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.GET, path = "/etapaDoisB")
	public ModelAndView adicionarAtividades(@RequestParam Long idDia, Long idEvento) {
		
		ModelAndView mv = new ModelAndView("area-acesso-adm/evento/criar-evento/etapa-2/etapa-2-B.html");
		
		try {
			Evento evento = eventoService.obterEvento(idEvento);
			mv.addObject("evento", evento);
			DiaDeEvento obterDiaDeEvento = diaDeEventoService.obterDiaDeEvento(idDia);
			mv.addObject("diaDeEvento", obterDiaDeEvento);
			Atividade atividade = new Atividade();
			atividade.setDiaDeEvento(obterDiaDeEvento);
			mv.addObject("atividade", atividade);
		} catch (Exception e) {
			mv.addObject("mensagem", e.getMessage());
			mv.addObject("cor", "danger");
		}
		
		
		return mv;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.GET, path = "/etapaTresA")
	public ModelAndView adicionarGrupos(@RequestParam Long id) {
		ModelAndView mv = new ModelAndView("area-acesso-adm/evento/criar-evento/etapa-3/etapa-3-A.html");
		try {
			Evento evento = eventoService.obterEvento(id);
			mv.addObject("evento", evento);
			Grupo grupo = new Grupo();
			grupo.setEvento(evento);
			mv.addObject("grupo", grupo);
			mv.addObject("cor", "success");
			mv.addObject("mensagem", "Evento Cadastrado com sucesso!");
		} catch (Exception e) {
			mv.addObject("mensagem", e.getMessage());
			mv.addObject("cor", "danger");
		}
		return mv;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.GET, path = "/etapaTresB")
	public ModelAndView adicionarParticipantesNoGrupos(@RequestParam Long idGrupo, Long idEvento) {
		ModelAndView mv = new ModelAndView("area-acesso-adm/evento/criar-evento/etapa-3/etapa-3-B.html");
		
		try {
			Evento evento = eventoService.obterEvento(idEvento);
			mv.addObject("evento", evento);
			Grupo grupo = grupoService.obterGrupo(idGrupo);
			mv.addObject("grupo", grupo);
			ParticipanteEvento participante = new ParticipanteEvento();
			participante.setGrupo(grupo);
			mv.addObject("participante", participante);
		} catch (Exception e) {
			mv.addObject("mensagem", e.getMessage());
			mv.addObject("cor", "danger");
		}
		
		return mv;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(path = "/editar")
	public ModelAndView editarEvento(@RequestParam(required = false) Long id) {
		
		ModelAndView mv = new ModelAndView("area-acesso-adm/evento/criar-evento/etapa-1.html");
		
		Evento evento;
		
		if (id == null) {
			evento = new Evento();
		} else {
			try {
				evento = eventoService.obterEvento(id);
			} catch (Exception e) {
				evento = new Evento();
				mv.addObject("mensagem", e.getMessage());
			}
		}
		
		mv.addObject("evento", evento);
		return mv;
	}


	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/deletar")
	public ModelAndView excluirEvento(@RequestParam Long id, RedirectAttributes redirectAttributes) {

		ModelAndView mv = new ModelAndView("redirect:/evento");

		try {
			eventoService.excluirEvento(id);
			redirectAttributes.addFlashAttribute("mensagem", "Evento excluído com sucesso");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir evento!" + e.getMessage());
		}

		return mv;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/detalharEvento")
	public ModelAndView detalharEvento(@RequestParam Long id, RedirectAttributes ra) {
		
		ModelAndView mv;
		try {
			mv = new ModelAndView("area-acesso-adm/evento/mostrar-evento.html");
			Evento evento = eventoService.obterEvento(id);
			mv.addObject("evento", evento);
		} catch (Exception e) {
			mv = new ModelAndView("redirect:/evento");
			ra.addFlashAttribute("mensagem", e.getMessage());
			ra.addFlashAttribute("cor", "danger");
		}

		return mv;
	}
	

	@RequestMapping(method = RequestMethod.GET, path = "home")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("area-acesso-adm/home.html");
		return mv;
	}
	
}
