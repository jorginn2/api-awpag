package com.algaworks.awpag.domain.service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.awpag.domain.exception.NegocioException;
import com.algaworks.awpag.domain.model.Cliente;
import com.algaworks.awpag.domain.model.Parcelamento;
import com.algaworks.awpag.domain.repository.ClienteRepository;
import com.algaworks.awpag.domain.repository.ParcelamentoRepository;

@Service
public class ParcelamentoService {

	private final ParcelamentoRepository parcelamentoRepository;
	private final CadastroClienteService cadastroClienteService;

	public ParcelamentoService(ParcelamentoRepository parcelamentoRepository,
			CadastroClienteService cadastroClienteService) {
		this.parcelamentoRepository = parcelamentoRepository;
		this.cadastroClienteService = cadastroClienteService;
	}

	@Transactional
	public Parcelamento cadastrar(Parcelamento novoParcelamento) {
		if (novoParcelamento.getId() != null) {
			throw new NegocioException("Parcelamento a ser criado não deve possuir um código!");
		}

		Cliente cliente = cadastroClienteService.buscar(novoParcelamento.getCliente().getId());

		novoParcelamento.setCliente(cliente);
		novoParcelamento.setDataCriacao(OffsetDateTime.now());

		return parcelamentoRepository.save(novoParcelamento);
	}
}
