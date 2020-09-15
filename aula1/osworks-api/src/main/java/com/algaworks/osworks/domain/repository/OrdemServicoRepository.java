package com.algaworks.osworks.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.osworks.domain.model.Cliente;
import com.algaworks.osworks.domain.model.OrdemServico;

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {
	
	/* 
	 * MySQL Config: 
	 * root : Mysql@123
	 * localapp : localapp@123
	 * 
	 */
	List<OrdemServico> findByDescricaoContaining(String descricao);
	
}
