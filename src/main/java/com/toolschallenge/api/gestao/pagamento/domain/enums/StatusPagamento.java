package com.toolschallenge.api.gestao.pagamento.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusPagamento {

    AUTORIZADO(),
    NEGADO(),
    CANCELADO()

}
