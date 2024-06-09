package com.algaworks.awpag.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteResumoModel {
	
	private Long id;
	private String nome;
	
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
	
	
}
