package com.algaworks.osworks.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.osworks.domain.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	/* 
	 * MySQL Config: 
	 * root : Mysql@123
	 * localapp : localapp@123
	 * 
	 */
	
	List<Cliente> findByNome(String nome);
	Cliente findByEmail(String email);
	List<Cliente> findByNomeContaining(String nome);
	
}
