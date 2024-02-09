package com.toolschallenge.api.gestao.pagamento.integration.processadora;

import static com.toolschallenge.api.gestao.pagamento.domain.enums.StatusPagamento.CANCELADO;
import static com.toolschallenge.api.gestao.pagamento.integration.processadora.util.RandomCollectionUtils.pickRandomExcept;
import static java.util.concurrent.ThreadLocalRandom.current;

import java.util.EnumSet;

import org.springframework.stereotype.Component;

import com.toolschallenge.api.gestao.pagamento.domain.enums.StatusPagamento;
import com.toolschallenge.api.gestao.pagamento.service.processadora.ProcessadoraPagamento;
import com.toolschallenge.api.gestao.pagamento.service.processadora.ProcessadoraPagamentoRequest;
import com.toolschallenge.api.gestao.pagamento.service.processadora.ProcessadoraPagamentoResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
class PagamentoIntegration implements ProcessadoraPagamento {

    public ProcessadoraPagamentoResponse comunicaPagamento(final ProcessadoraPagamentoRequest request) {
        log.info("Realizando comunicação do pagamento: {}", request);

        EnumSet.complementOf(EnumSet.of(CANCELADO));

        return ComunicaPagamentoIntegrationResponse.builder()
            .nsu(current().nextLong())
            .codigoAutorizacao(current().nextLong())
            .status(pickRandomExcept(StatusPagamento.values(), CANCELADO))
            .build();
    }

}
