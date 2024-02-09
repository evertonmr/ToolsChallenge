package com.toolschallenge.api.gestao.pagamento.request;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toolschallenge.api.gestao.pagamento.domain.enums.TipoPagamento;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class PagamentoRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -7456543538955547927L;

    @Valid
    private Transacao transacao;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Transacao implements Serializable{

        @Serial
        private static final long serialVersionUID = -5551641399434302331L;

        @NotBlank
        private String cartao;

        @NotNull
        private String id;

        @Valid
        private Descricao descricao;

        @Valid
        private FormaPagamento formaPagamento;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Descricao implements Serializable {

        @Serial
        private static final long serialVersionUID = -1357262728160392973L;

        @NotNull
        @Positive
        private BigDecimal valor;

        @NotNull
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        private LocalDateTime dataHora;

        @NotBlank
        private String estabelecimento;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FormaPagamento implements Serializable{

        @Serial
        private static final long serialVersionUID = 7359943315069681401L;

        @NotNull
        private TipoPagamento tipo;

        @NotNull
        private Integer parcelas;

    }
}

