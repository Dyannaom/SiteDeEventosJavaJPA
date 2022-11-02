package br.com.gft.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gft.entities.Usuario;
import br.com.gft.repositories.UsuarioRepository;

@Service
@Transactional
public class UsuarioService implements UserDetailsService {

	final UsuarioRepository usuarioRepository;
	
	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	
	

	public Usuario salvarUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	public List<Usuario> listarUsuarios() {
		return usuarioRepository.findAll();
	}

	public Usuario obterUsuario(UUID id) throws Exception {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if (usuario.isEmpty())
			throw new Exception("Usuario não encontrado!");
		else
			return usuario.get();
	}

	public void deletarUsuario(UUID id) throws Exception {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if (usuario.isEmpty())
			throw new Exception("Usuario não encontrado!");
		else
			usuarioRepository.delete(usuario.get());
	}

	public Usuario obterUsuarioPelasQuatroLetras(String quatroLetras) throws Exception {
		Optional<Usuario> usuario = usuarioRepository.findByQuatroLetras(quatroLetras);
		if (usuario.isEmpty())
			throw new Exception("Usuario não encontrado!");
		else
			return usuario.get();
	}

	public Optional<Usuario> verificarSeUsuarioComEssasQuatroLetrasJaExiste(String quatroLetras){
		return usuarioRepository.findByQuatroLetras(quatroLetras);
	}

 

	public Usuario buscarPorEmail (String email) {
		return usuarioRepository.findByEmail (email);
	}
	
	

	public void desativarUsuario(UUID id) throws Exception {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if (usuario.isEmpty())
			throw new Exception("Usuario não encontrado!");
		else
			usuario.get().setStatus(false);
	}
	
	public void ativarUsuario(UUID id) throws Exception{
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if (usuario.isEmpty())
			throw new Exception("Usuario não encontrado!");
		else
			usuario.get().setStatus(true);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	}


}
