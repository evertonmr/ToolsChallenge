package com.toolschallenge.api.gestao.pagamento.integration.processadora.request;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toolschallenge.api.gestao.pagamento.domain.enuns.TipoPagamento;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class ComunicaPagamentoIntegrationRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -2602385217271806992L;

    private final Transacao transacao;

    @Getter
    @Builder
    public static final class Transacao implements Serializable {

        @Serial
        private static final long serialVersionUID = 1735045992274429686L;

        private final String cartao;
        private final Descricao descricao;
        private final FormaPagamento formaPagamento;

    }

    @Getter
    @Builder
    public static class Descricao implements Serializable {

        @Serial
        private static final long serialVersionUID = -8384967632595823060L;

        private final BigDecimal valor;

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        private final LocalDateTime dataHora;

        private final String estabelecimento;

    }

    @Getter
    @Builder
    public static class FormaPagamento implements Serializable {

        @Serial
        private static final long serialVersionUID = -2746825524203657994L;

        private final TipoPagamento tipo;
        private final Integer parcelas;

    }
}
