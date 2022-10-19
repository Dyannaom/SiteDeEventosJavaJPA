package br.com.gft.aagp.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Atividade {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private LocalDateTime dataInicio;
	private LocalDateTime dataFinal;
	
	
	
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
	public LocalDateTime getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(LocalDateTime dataInicio) {
		this.dataInicio = dataInicio;
	}
	public LocalDateTime getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(LocalDateTime dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	

}
