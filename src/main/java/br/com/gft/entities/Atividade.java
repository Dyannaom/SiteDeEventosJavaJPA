package br.com.gft.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class Atividade {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty (message = " Não pode ser em branco ")
	private String nome;
	@ManyToOne
	private DiaDeEvento diaDeEvento;

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

	public DiaDeEvento getDiaDeEvento() {
		return diaDeEvento;
	}

	public void setDiaDeEvento(DiaDeEvento diaDeEvento) {
		this.diaDeEvento = diaDeEvento;
	}
	
	
	
	
	

}
