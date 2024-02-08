package com.toolschallenge.api.gestao.pagamento.sercive;

import org.springframework.stereotype.Service;

import com.toolschallenge.api.gestao.pagamento.integration.processadora.PagamentoIntegration;
import com.toolschallenge.api.gestao.pagamento.integration.processadora.mapper.ComunicaPagamentoRequestMapStruct;
import com.toolschallenge.api.gestao.pagamento.integration.processadora.response.ComunicaPagamentoIntegrationResponse;
import com.toolschallenge.api.gestao.pagamento.mapper.PagamentoResponseMapStruct;
import com.toolschallenge.api.gestao.pagamento.entity.PagamentoEntity;
import com.toolschallenge.api.gestao.pagamento.repository.PagamentoRepository;
import com.toolschallenge.api.gestao.pagamento.request.PagamentoRequest;
import com.toolschallenge.api.gestao.pagamento.response.PagamentoResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RealizaPagamentoService {

    private final PagamentoRepository repository;
    private final PagamentoIntegration integration;
    private final ComunicaPagamentoRequestMapStruct requestMapStruct;
    private final PagamentoResponseMapStruct responseMapStruct;

    public PagamentoResponse pagar(final PagamentoRequest request) {
        log.info("Realizando pagamento para requisição: {}", request);

        final var comunicaPagamentoResponse = integration.comunicaPagamento(requestMapStruct.apply(request));

        final var entity = registraPagamento(request, comunicaPagamentoResponse);

        return responseMapStruct.apply(entity);
    }

    private PagamentoEntity registraPagamento(final PagamentoRequest request,
        final ComunicaPagamentoIntegrationResponse comunicaPagamentoResponse) {
        final var entity = new PagamentoEntity();
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
