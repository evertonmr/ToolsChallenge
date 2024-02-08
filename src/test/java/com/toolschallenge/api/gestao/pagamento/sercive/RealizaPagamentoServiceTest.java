package com.toolschallenge.api.gestao.pagamento.sercive;

import static java.util.concurrent.ThreadLocalRandom.current;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mapstruct.factory.Mappers.getMapper;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.toolschallenge.api.gestao.pagamento.entity.PagamentoEntity;
import com.toolschallenge.api.gestao.pagamento.fixture.Fixture;
import com.toolschallenge.api.gestao.pagamento.integration.processadora.PagamentoIntegration;
import com.toolschallenge.api.gestao.pagamento.integration.processadora.mapper.ComunicaPagamentoRequestMapStruct;
import com.toolschallenge.api.gestao.pagamento.integration.processadora.response.ComunicaPagamentoIntegrationResponse;
import com.toolschallenge.api.gestao.pagamento.mapper.PagamentoResponseMapStruct;
import com.toolschallenge.api.gestao.pagamento.repository.PagamentoRepository;
import com.toolschallenge.api.gestao.pagamento.request.PagamentoRequest;
import com.toolschallenge.api.gestao.pagamento.response.PagamentoResponse;

@ExtendWith(MockitoExtension.class)
public class RealizaPagamentoServiceTest {

    @InjectMocks
    private RealizaPagamentoService service;

    @Mock
    private PagamentoIntegration integration;

    @Mock
    private PagamentoRepository repository;

    @Spy
    private ComunicaPagamentoRequestMapStruct requestMapStruct = getMapper(ComunicaPagamentoRequestMapStruct.class);

    @Spy
    private PagamentoResponseMapStruct responseMapStruct = getMapper(PagamentoResponseMapStruct.class);

    @Test
    public void deveRealizarPagamentoComSucesso() {
        final PagamentoRequest request = Fixture.make(PagamentoRequest.builder()).build();
        final ComunicaPagamentoIntegrationResponse integrationResponse = Fixture
            .make(ComunicaPagamentoIntegrationResponse.builder()).build();
        final PagamentoEntity entity = new PagamentoEntity();
        entity.setCartao(request.getTransacao().getCartao());
        entity.setValor(request.getTransacao().getDescricao().getValor());
        entity.setDataHora(request.getTransacao().getDescricao().getDataHora());
        entity.setEstabelecimento(request.getTransacao().getDescricao().getEstabelecimento());
        entity.setTipo(request.getTransacao().getFormaPagamento().getTipo());
        entity.setParcelas(request.getTransacao().getFormaPagamento().getParcelas());

        entity.setNsu(integrationResponse.getNsu());
        entity.setCodigoAutorizacao(integrationResponse.getCodigoAutorizacao());
        entity.setStatus(integrationResponse.getStatus());

        when(integration.comunicaPagamento(any())).thenReturn(integrationResponse);
        when(repository.save(any())).thenReturn(entity);

        final PagamentoResponse resultado = service.pagamento(request);

        verify(integration).comunicaPagamento(any());
        verify(requestMapStruct).apply(any());
        verify(responseMapStruct).apply(any());

        assertEquals(request.getTransacao().getCartao(), resultado.getTransacao().getCartao());
        assertEquals(request.getTransacao().getDescricao().getEstabelecimento(),
            resultado.getTransacao().getDescricao().getEstabelecimento());
        assertEquals(request.getTransacao().getDescricao().getDataHora(),
            resultado.getTransacao().getDescricao().getDataHora());
        assertEquals(request.getTransacao().getDescricao().getValor(),
            resultado.getTransacao().getDescricao().getValor());
        assertEquals(request.getTransacao().getFormaPagamento().getParcelas(),
            resultado.getTransacao().getFormaPagamento().getParcelas());
        assertEquals(request.getTransacao().getFormaPagamento().getTipo(),
            resultado.getTransacao().getFormaPagamento().getTipo());
        assertEquals(integrationResponse.getCodigoAutorizacao(),
            resultado.getTransacao().getDescricao().getCodigoAutorizacao());
        assertEquals(integrationResponse.getNsu(),
            resultado.getTransacao().getDescricao().getNsu());
        assertEquals(integrationResponse.getStatus(),
            resultado.getTransacao().getStatus());

    }

}