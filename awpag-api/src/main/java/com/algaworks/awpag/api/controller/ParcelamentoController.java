package com.algaworks.awpag.api.controller;

import java.util.List;

import javax.lang.model.element.VariableElement;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.awpag.api.assembler.ParcelamentoAssembler;
import com.algaworks.awpag.api.model.ParcelamentoModel;
import com.algaworks.awpag.api.model.input.ParcelamentoInput;
import com.algaworks.awpag.domain.exception.NegocioException;
import com.algaworks.awpag.domain.model.Parcelamento;
import com.algaworks.awpag.domain.repository.ParcelamentoRepository;
import com.algaworks.awpag.domain.service.ParcelamentoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/parcelamentos")
public class ParcelamentoController {

	private final ParcelamentoRepository parcelamentoRepository;
	private final ParcelamentoService parcelamentoService;
	private final ParcelamentoAssembler parcelamentoAssembler;

	public ParcelamentoController(ParcelamentoRepository parcelamentoRepository,
			ParcelamentoService parcelamentoService, ParcelamentoAssembler parcelamentoAssembler) {
		this.parcelamentoRepository = parcelamentoRepository;
		this.parcelamentoService = parcelamentoService;
		this.parcelamentoAssembler = parcelamentoAssembler;
	}

	@GetMapping
	public List<ParcelamentoModel> listar() {
		return parcelamentoAssembler.toCollectionModel(parcelamentoRepository.findAll());
	}

	@GetMapping("/{parcelamentoId}")
	public ResponseEntity<ParcelamentoModel> buscar(@PathVariable Long parcelamentoId) {

		return parcelamentoRepository.findById(parcelamentoId).map(parcelamentoAssembler::toModel)
				.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ParcelamentoModel cadastrar(@Valid @RequestBody ParcelamentoInput parcelamentoInput) {

		Parcelamento novoParcelamento = parcelamentoAssembler.toEntity(parcelamentoInput);
		Parcelamento parcelamentoCadastrado = parcelamentoService.cadastrar(novoParcelamento);
		return parcelamentoAssembler.toModel(parcelamentoCadastrado);

	}

}
