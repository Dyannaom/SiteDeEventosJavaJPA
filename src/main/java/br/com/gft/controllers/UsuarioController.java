package br.com.gft.controllers;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.gft.entities.Usuario;
import br.com.gft.services.UsuarioService;

@Controller
@RequestMapping("usuario")
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;

	
	@RequestMapping
	public ModelAndView loginUsuario() {
		ModelAndView mv = new ModelAndView("area-acesso-adm/usuario/form/login.html");
		mv.addObject("usuario", new Usuario());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, path = "entrar")
	public ModelAndView entrarComoAdiministrador(Usuario usuario, RedirectAttributes ra) {

		ModelAndView mv;
		try {
			Usuario usuarioEncontrado = usuarioService.obterUsuarioPelasQuatroLetras(usuario.getQuatroLetras());
			if (!usuarioEncontrado.getSenha().equals(usuario.getSenha())) {
				mv = new ModelAndView("area-acesso-adm/usuario/form/login.html");
				usuario.setSenha(null);
				mv.addObject("usuario", usuario);
				mv.addObject("mensagem", "Senha incorreta!");
				mv.addObject("cor", "danger");

			} else {
				mv = new ModelAndView("redirect:/usuario/home");
				ra.addFlashAttribute("usuario", usuarioEncontrado);
				ra.addFlashAttribute("mensagem", "Entrou como administrador!");
			}

		} catch (Exception e) {
			mv = new ModelAndView("area-acesso-adm/usuario/form/login.html");
			mv.addObject("usuario", new Usuario());
			mv.addObject("mensagem", e.getMessage());
			mv.addObject("cor", "danger");
		}

		return mv;

	}

	@RequestMapping(method = RequestMethod.GET, path = "editar")
	public ModelAndView editarUsuario(@RequestParam(required = false) UUID id) {
		ModelAndView mv = new ModelAndView("area-acesso-adm/usuario/form/cadastro.html");

		if (id == null) {
			mv.addObject("usuario", new Usuario());
		} else {
			try {
				mv.addObject("usuario", usuarioService.obterUsuario(id));
			} catch (Exception e) {
				mv.addObject("messagem", e.getMessage());
			}
		}

		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, path = "salvar")
	public ModelAndView salvarUsuarios(@Valid Usuario usuario, BindingResult bindingResult, RedirectAttributes ra) {
		ModelAndView mv;
		boolean usuarioComEssasQuatroLetrasJaExiste = usuarioService
				.verificarSeUsuarioComEssasQuatroLetrasJaExiste(usuario.getQuatroLetras()).isEmpty();
		
		boolean usuarioComEsseEmailJaExiste = usuarioService
				.verificarSeUsuarioComEssasQuatroLetrasJaExiste(usuario.getEmail()).isEmpty();

		if (!bindingResult.hasErrors() && usuarioComEssasQuatroLetrasJaExiste && usuarioComEsseEmailJaExiste) {
			usuario.setStatus(true);
			usuarioService.salvarUsuario(usuario);
			mv = new ModelAndView("redirect:/usuario");
			ra.addFlashAttribute("mensagem", "Usuario salvo com sucesso!");
			ra.addFlashAttribute("cor", "success");
		} else {
			mv = new ModelAndView("area-acesso-adm/usuario/form/cadastro.html");
			if(!usuarioComEssasQuatroLetrasJaExiste && !usuarioComEsseEmailJaExiste)
				mv.addObject("mensagem", "Usuario Com essas 4 letras e com esse email já Existem!");
			else {
				if(!usuarioComEssasQuatroLetrasJaExiste)
					mv.addObject("mensagem", "Usuario com essas 4 Letras já existe");
				
				if(!usuarioComEsseEmailJaExiste)
					mv.addObject("mensagem", "Usuario com esse email já existe");
			}
			mv.addObject("cor", "danger");
		}

		return mv;
	}

	@RequestMapping(method = RequestMethod.GET, path = "obter")
	public ModelAndView pegarUsuario(@RequestParam UUID id) {
		ModelAndView mv = new ModelAndView("area-acesso-adm/usuario/perfil.html");
		try {
			mv.addObject("usuario", usuarioService.obterUsuario(id));
		} catch (Exception e) {
			mv.addObject("mensagem", e.getMessage());
		}
		return mv;
	}

	@RequestMapping(method = RequestMethod.GET, path = "deletar")
	public ModelAndView deletarUsuario(@RequestParam UUID id) {
		ModelAndView mv = new ModelAndView();
		try {
			usuarioService.deletarUsuario(id);
		} catch (Exception e) {
			mv.addObject("mensagem", e.getMessage());
		}
		return mv;
	}

	@RequestMapping(method = RequestMethod.GET, path = "desativar")
	public ModelAndView desativarUsuario(@RequestParam UUID id) {
		ModelAndView mv = new ModelAndView();
		try {
			usuarioService.desativarUsuario(id);
		} catch (Exception e) {
			mv.addObject("mensagem", e.getMessage());
		}
		return mv;
	}


}
