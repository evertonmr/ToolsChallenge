package com.toolschallenge.api.gestao.pagamento.integration.processadora;

import static com.toolschallenge.api.gestao.pagamento.util.RandomCollectionUtils.pickRandom;
import static java.util.concurrent.ThreadLocalRandom.current;

import org.springframework.stereotype.Component;

import com.toolschallenge.api.gestao.pagamento.integration.processadora.request.ComunicaPagamentoIntegrationRequest;
import com.toolschallenge.api.gestao.pagamento.integration.processadora.response.ComunicaPagamentoIntegrationResponse;
import com.toolschallenge.api.gestao.pagamento.domain.enuns.StatusPagamento;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PagamentoIntegration {

    public ComunicaPagamentoIntegrationResponse comunicaPagamento(final ComunicaPagamentoIntegrationRequest request) {
        log.info("Realizando comunicação do pagamento: {}", request);

        return ComunicaPagamentoIntegrationResponse.builder()
            .nsu(current().nextLong())
            .codigoAutorizacao(current().nextLong())
            .status(pickRandom(StatusPagamento.values()))
            .build();
    }

}
