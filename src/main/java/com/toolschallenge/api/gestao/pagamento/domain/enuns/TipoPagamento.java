package com.toolschallenge.api.gestao.pagamento.domain.enuns;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TipoPagamento {
    AVISTA("À vista"),
    PARCELADO_LOJA("Parcelado loja"),
    PARCELADO_EMISSOR("Parcelado emissor");

    private final String descrição;
}
