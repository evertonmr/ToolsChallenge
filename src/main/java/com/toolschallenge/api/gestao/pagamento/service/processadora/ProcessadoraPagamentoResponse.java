package com.toolschallenge.api.gestao.pagamento.service.processadora;

import com.toolschallenge.api.gestao.pagamento.domain.enums.StatusPagamento;

public interface ProcessadoraPagamentoResponse {

    Long getNsu();
    Long getCodigoAutorizacao();
    StatusPagamento getStatus();

}
