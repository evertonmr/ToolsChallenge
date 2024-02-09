package com.toolschallenge.api.gestao.pagamento.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.toolschallenge.api.gestao.pagamento.entity.PagamentoEntity;
import com.toolschallenge.api.gestao.pagamento.response.PagamentoResponse;

@Mapper(componentModel = "spring")
public interface PagamentoResponseMapper {

    @Mappings({
        @Mapping(target = "transacao.id", source = "idTransacao"),
        @Mapping(target = "transacao.cartao", source = "cartao"),
        @Mapping(target = "transacao.descricao.valor", source = "valor"),
        @Mapping(target = "transacao.descricao.dataHora", source = "dataHora"),
        @Mapping(target = "transacao.descricao.estabelecimento", source = "estabelecimento"),
        @Mapping(target = "transacao.formaPagamento.tipo", source = "tipo"),
        @Mapping(target = "transacao.formaPagamento.parcelas", source = "parcelas"),
        @Mapping(target = "transacao.descricao.nsu", source = "nsu"),
        @Mapping(target = "transacao.descricao.codigoAutorizacao", source = "codigoAutorizacao"),
        @Mapping(target = "transacao.status", source = "status"),
    })
    PagamentoResponse apply(final PagamentoEntity entity);

}
