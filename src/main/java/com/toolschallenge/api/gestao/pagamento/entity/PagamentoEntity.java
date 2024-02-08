package com.toolschallenge.api.gestao.pagamento.entity;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toolschallenge.api.gestao.pagamento.domain.enuns.StatusPagamento;
import com.toolschallenge.api.gestao.pagamento.domain.enuns.TipoPagamento;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "PAGAMENTO")
public class PagamentoEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 7848332250566901115L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String cartao;

    private BigDecimal valor;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataHora;

    private String estabelecimento;

    @Enumerated(EnumType.STRING)
    private TipoPagamento tipo;

    private Integer parcelas;

    private Long nsu;

    private Long codigoAutorizacao;

    @Enumerated(EnumType.STRING)
    private StatusPagamento status;
}