package com.algaworks.osworks.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.osworks.api.model.OrdemServicoInput;
import com.algaworks.osworks.api.model.OrdemServicoModel;
import com.algaworks.osworks.domain.model.OrdemServico;
import com.algaworks.osworks.domain.repository.OrdemServicoRepository;
import com.algaworks.osworks.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private OrdemServicoRepository repo;
	
	@Autowired
	private GestaoOrdemServicoService gestaoOS;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoModel criar(@Valid @RequestBody OrdemServicoInput osIn) {
		return toModel(gestaoOS.criar(toEntity(osIn)));
	}

	@GetMapping
	public List<OrdemServicoModel> listar() {
		return toCollectionModel(repo.findAll());
	}
	
	
	@GetMapping("/{ordemServicoId}")
	public ResponseEntity<OrdemServicoModel> buscar(@PathVariable Long ordemServicoId) {
		Optional<OrdemServico> os = repo.findById(ordemServicoId);
		if (os.isPresent()) {
			OrdemServicoModel model = toModel(os.get());
			
			return ResponseEntity.ok(model);
		}
		return ResponseEntity.notFound().build();
	}
	
	
	@PutMapping("/{ordemServicoId}/finalizacao")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void finalizar(@Valid @PathVariable Long ordemServicoId) {
		gestaoOS.finalizarOrdemServico(ordemServicoId);
	}
//	
//	@DeleteMapping("/{clienteId}")
//	public ResponseEntity<Void> remover(@PathVariable Long clienteId){
//		if (!repo.existsById(clienteId))
//			return ResponseEntity.notFound().build();
//		gestaoOS.excluir(clienteId);
//		return ResponseEntity.noContent().build();
//	}

	private OrdemServicoModel toModel(OrdemServico os) {
		return mapper.map(os, OrdemServicoModel.class);
	}
	
	private List<OrdemServicoModel> toCollectionModel(List<OrdemServico> osList) {
		return osList.stream()
				.map(ordemServico -> toModel(ordemServico))
				.collect(Collectors.toList());
	}
	
	private OrdemServico toEntity(OrdemServicoInput osIn) {
		return mapper.map(osIn, OrdemServico.class);
	}
}
