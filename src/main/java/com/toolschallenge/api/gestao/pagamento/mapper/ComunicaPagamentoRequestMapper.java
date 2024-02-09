package com.toolschallenge.api.gestao.pagamento.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.toolschallenge.api.gestao.pagamento.request.PagamentoRequest;
import com.toolschallenge.api.gestao.pagamento.service.processadora.ProcessadoraPagamentoRequest;

@Mapper(componentModel = "spring")
public interface ComunicaPagamentoRequestMapper {

    @Mapping(target = "id", source = "transacao.id")
    ProcessadoraPagamentoRequest apply(final PagamentoRequest request);

}
