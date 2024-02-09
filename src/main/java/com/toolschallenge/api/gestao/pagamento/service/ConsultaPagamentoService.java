package com.toolschallenge.api.gestao.pagamento.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.toolschallenge.api.gestao.pagamento.domain.enums.ErrorType;
import com.toolschallenge.api.gestao.pagamento.exception.ClientErrorException;
import com.toolschallenge.api.gestao.pagamento.mapper.PagamentoResponseMapper;
import com.toolschallenge.api.gestao.pagamento.repository.PagamentoRepository;
import com.toolschallenge.api.gestao.pagamento.response.PagamentoResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultaPagamentoService {

    private final PagamentoRepository repository;
    private final PagamentoResponseMapper pagamentoResponseMapper;

    public PagamentoResponse consultar(final String id) {
        log.info("Realizando consulta de pagamento pelo identificador: {}", id);

        var pagametnoEntity = repository.findByIdTransacao(id).orElseThrow(() -> {
            throw new ClientErrorException(ErrorType.NOT_FOUND, "Nenhum registro encontrado");
        });

        return pagamentoResponseMapper.apply(pagametnoEntity);
    }

    public Page<PagamentoResponse> consultar(final Pageable pageable) {
        log.info("Realizando consulta de todos pagamentos");

        var pagamentoEntities = repository.findAll(pageable);

        return pagamentoEntities.map(pagamentoResponseMapper::apply);
    }

}
