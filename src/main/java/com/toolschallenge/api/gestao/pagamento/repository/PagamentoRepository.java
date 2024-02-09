package com.toolschallenge.api.gestao.pagamento.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toolschallenge.api.gestao.pagamento.entity.PagamentoEntity;

@Repository
public interface PagamentoRepository extends JpaRepository<PagamentoEntity, String> {

    Optional<PagamentoEntity> findByIdTransacao(String idTransacao);

}
