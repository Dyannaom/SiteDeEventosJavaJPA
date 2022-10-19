package br.com.gft.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gft.entities.Usuario;
import br.com.gft.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public Usuario salvarUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	public List<Usuario> listarUsuarios() {
		return usuarioRepository.findAll();
	}

	public Usuario obterUsuario(Long id) throws Exception{
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if(usuario.isEmpty())
			throw new Exception("Usuario não encontrado!");
		else
			return usuario.get();
	}

	public void deletarUsuario(Long id) throws Exception {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if(usuario.isEmpty())
			throw new Exception("Usuario não encontrado!");
		else
			usuarioRepository.delete(usuario.get());
	}

}
