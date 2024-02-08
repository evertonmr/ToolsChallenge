package com.toolschallenge.api.gestao.pagamento.response;


import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toolschallenge.api.gestao.pagamento.domain.enuns.StatusPagamento;
import com.toolschallenge.api.gestao.pagamento.domain.enuns.TipoPagamento;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class PagamentoResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 4581952593816589615L;

    private final Transacao transacao;

    @Getter
    @Builder
    public static final class Transacao implements Serializable {

        @Serial
        private static final long serialVersionUID = -6839904594520282354L;

        private final Long id;
        private final String cartao;
        private final Descricao descricao;
        private final StatusPagamento status;
        private final FormaPagamento formaPagamento;

    }

    @Getter
    @Builder
    public static class Descricao implements Serializable {

        @Serial
        private static final long serialVersionUID = -843564683157644283L;

        private final BigDecimal valor;

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        private final LocalDateTime dataHora;

        private final String estabelecimento;
        private final Long nsu;
        private final Long codigoAutorizacao;

    }

    @Getter
    @Builder
    public static class FormaPagamento implements Serializable {

        @Serial
        private static final long serialVersionUID = 6950211343230820038L;

        private final TipoPagamento tipo;
        private final Integer parcelas;

    }

}
