package com.toolschallenge.api.gestao.pagamento.domain.enuns;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusPagamento {

    AUTORIZADO("Autorizado"),
    NEGADO("Negado");

    private final String descricao;
}
