package br.com.gft.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Evento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataInicio;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataFinal;
	
	@OneToMany
	private List<Atividade> listaDeAtividadesDoEvento;
	@OneToMany
	private List<Grupo> listaDeGruposDoEvento;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public LocalDate getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}
	public LocalDate getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}
	public List<Atividade> getListaDeAtividadesDoEvento() {
		return listaDeAtividadesDoEvento;
	}
	public void setListaDeAtividadesDoEvento(List<Atividade> listaDeAtividadesDoEvento) {
		this.listaDeAtividadesDoEvento = listaDeAtividadesDoEvento;
	}
	public List<Grupo> getListaDeGruposDoEvento() {
		return listaDeGruposDoEvento;
	}
	public void setListaDeGruposDoEvento(List<Grupo> listaDeGruposDoEvento) {
		this.listaDeGruposDoEvento = listaDeGruposDoEvento;
	}
	
	
	
}
