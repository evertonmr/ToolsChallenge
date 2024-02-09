package com.toolschallenge.api.gestao.pagamento.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TipoPagamento {
    AVISTA(),
    PARCELADO_LOJA(),
    PARCELADO_EMISSOR()

}
