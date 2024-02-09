package com.toolschallenge.api.gestao.pagamento.integration.processadora;

import java.io.Serial;
import java.io.Serializable;

import com.toolschallenge.api.gestao.pagamento.domain.enums.StatusPagamento;
import com.toolschallenge.api.gestao.pagamento.service.processadora.ProcessadoraPagamentoResponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
class ComunicaPagamentoIntegrationResponse implements Serializable, ProcessadoraPagamentoResponse {

    @Serial
    private static final long serialVersionUID = 5075403129734308781L;

    private final Long nsu;
    private final Long codigoAutorizacao;
    private final StatusPagamento status;

}
