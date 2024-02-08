package com.toolschallenge.api.gestao.pagamento.integration.processadora.mapper;

import org.mapstruct.Mapper;

import com.toolschallenge.api.gestao.pagamento.integration.processadora.request.ComunicaPagamentoIntegrationRequest;
import com.toolschallenge.api.gestao.pagamento.request.PagamentoRequest;

@Mapper(componentModel = "spring")
public interface ComunicaPagamentoRequestMapStruct {

    ComunicaPagamentoIntegrationRequest apply(final PagamentoRequest request);

}
