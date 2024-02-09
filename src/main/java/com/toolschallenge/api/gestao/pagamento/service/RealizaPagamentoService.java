package com.toolschallenge.api.gestao.pagamento.service;

import org.springframework.stereotype.Service;

import com.toolschallenge.api.gestao.pagamento.mapper.ComunicaPagamentoRequestMapper;
import com.toolschallenge.api.gestao.pagamento.mapper.PagamentoResponseMapper;
import com.toolschallenge.api.gestao.pagamento.entity.PagamentoEntity;
import com.toolschallenge.api.gestao.pagamento.repository.PagamentoRepository;
import com.toolschallenge.api.gestao.pagamento.request.PagamentoRequest;
import com.toolschallenge.api.gestao.pagamento.response.PagamentoResponse;
import com.toolschallenge.api.gestao.pagamento.service.processadora.ProcessadoraPagamento;
import com.toolschallenge.api.gestao.pagamento.service.processadora.ProcessadoraPagamentoResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RealizaPagamentoService {

    private final PagamentoRepository repository;
    private final ProcessadoraPagamento integration;
    private final ComunicaPagamentoRequestMapper requestMapper;
    private final PagamentoResponseMapper responseMapper;

    @Transactional
    public PagamentoResponse pagar(final PagamentoRequest request) {
        log.info("Realizando pagamento para requisição: {}", request);

        final var comunicaPagamentoResponse = integration.comunicaPagamento(requestMapper.apply(request));

        final var entity = registraPagamento(request, comunicaPagamentoResponse);

        return responseMapper.apply(entity);
    }

    private PagamentoEntity registraPagamento(final PagamentoRequest request,
        final ProcessadoraPagamentoResponse comunicaPagamentoResponse) {
        final var entity = new PagamentoEntity();
        entity.setIdTransacao(request.getTransacao().getId());
        entity.setCartao(request.getTransacao().getCartao());
        entity.setValor(request.getTransacao().getDescricao().getValor());
        entity.setDataHora(request.getTransacao().getDescricao().getDataHora());
        entity.setEstabelecimento(request.getTransacao().getDescricao().getEstabelecimento());
        entity.setTipo(request.getTransacao().getFormaPagamento().getTipo());
        entity.setParcelas(request.getTransacao().getFormaPagamento().getParcelas());

        entity.setNsu(comunicaPagamentoResponse.getNsu());
        entity.setCodigoAutorizacao(comunicaPagamentoResponse.getCodigoAutorizacao());
        entity.setStatus(comunicaPagamentoResponse.getStatus());

        repository.save(entity);
        return entity;
    }
}
