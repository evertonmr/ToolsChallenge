package com.toolschallenge.api.gestao.pagamento.integration.processadora.response;

import java.io.Serial;
import java.io.Serializable;

import com.toolschallenge.api.gestao.pagamento.domain.enuns.StatusPagamento;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ComunicaPagamentoIntegrationResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 5075403129734308781L;

    private final Long nsu;
    private final Long codigoAutorizacao;
    private final StatusPagamento status;

}
