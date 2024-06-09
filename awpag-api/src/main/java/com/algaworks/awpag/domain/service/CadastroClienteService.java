package com.algaworks.awpag.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.awpag.domain.exception.NegocioException;
import com.algaworks.awpag.domain.model.Cliente;
import com.algaworks.awpag.domain.repository.ClienteRepository;

@Service
public class CadastroClienteService {

	private final ClienteRepository clienteRepository;

	public CadastroClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}
	
	public Cliente buscar(Long clienteId) {
		return clienteRepository.findById(clienteId)
		.orElseThrow(() -> new NegocioException("Cliente não encontrado!"));
	}
	

	@Transactional
	public Cliente salvar(Cliente cliente) {
		boolean emailEmUso = clienteRepository.findByEmail(cliente.getEmail())
				.filter(c -> !c.equals(cliente))
				.isPresent();
		if (emailEmUso) {
			throw new NegocioException("Já existe um cliente com este e-mail!");
		}

		return clienteRepository.save(cliente);
	}

	@Transactional
	public void excluir(Long clienteId) {
		clienteRepository.deleteById(clienteId);
	}

}
