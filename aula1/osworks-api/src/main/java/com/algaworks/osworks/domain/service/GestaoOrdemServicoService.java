package com.algaworks.osworks.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.osworks.domain.exception.NegocioException;
import com.algaworks.osworks.domain.model.Cliente;
import com.algaworks.osworks.domain.model.Comentario;
import com.algaworks.osworks.domain.model.OrdemServico;
import com.algaworks.osworks.domain.model.StatusOrdemServico;
import com.algaworks.osworks.domain.repository.ClienteRepository;
import com.algaworks.osworks.domain.repository.ComentarioRepository;
import com.algaworks.osworks.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {
	@Autowired
	private OrdemServicoRepository osRepo;
	
	@Autowired
	private ComentarioRepository comRepo;
	
	@Autowired
	private ClienteRepository cliRepo;
	
	public OrdemServico criar(OrdemServico os) {
		Cliente cliente = cliRepo.findById(os.getCliente().getId())
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));
		
		os.setCliente(cliente);
		os.setStatus(StatusOrdemServico.ABERTA);
		os.setDataAbertura(OffsetDateTime.now());
		
		var saved = osRepo.save(os);
		
		return saved;
	}
	
	public Comentario adicionarComentario(Long osId, String descricao) {
		OrdemServico os = buscar(osId);
		
		Comentario comentario = new Comentario();
		comentario.setOrdemServico(os);
		comentario.setDescricao(descricao);
		comentario.setDataEnvio(OffsetDateTime.now());
		
		return comRepo.save(comentario);
	}

	
	public void finalizarOrdemServico(Long ordemServicoId) {
		OrdemServico os = buscar(ordemServicoId);
		os.finalizar();
		osRepo.save(os);
	}
	
	private OrdemServico buscar(Long osId) {
		OrdemServico os = osRepo.findById(osId).orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço inexistente"));
		return os;
	}
	
}
