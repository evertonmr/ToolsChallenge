package com.toolschallenge.api.gestao.pagamento.service.processadora;


public interface ProcessadoraPagamento {

    ProcessadoraPagamentoResponse comunicaPagamento(final ProcessadoraPagamentoRequest request);

}
