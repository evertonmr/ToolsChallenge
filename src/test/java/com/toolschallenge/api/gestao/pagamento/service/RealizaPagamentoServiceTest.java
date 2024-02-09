package com.toolschallenge.api.gestao.pagamento.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mapstruct.factory.Mappers.getMapper;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.toolschallenge.api.gestao.pagamento.domain.enums.StatusPagamento;
import com.toolschallenge.api.gestao.pagamento.entity.PagamentoEntity;
import com.toolschallenge.api.gestao.pagamento.exception.ServerErrorException;
import com.toolschallenge.api.gestao.pagamento.fixture.Fixture;
import com.toolschallenge.api.gestao.pagamento.mapper.ComunicaPagamentoRequestMapper;
import com.toolschallenge.api.gestao.pagamento.mapper.PagamentoResponseMapper;
import com.toolschallenge.api.gestao.pagamento.repository.PagamentoRepository;
import com.toolschallenge.api.gestao.pagamento.request.PagamentoRequest;
import com.toolschallenge.api.gestao.pagamento.response.PagamentoResponse;
import com.toolschallenge.api.gestao.pagamento.service.processadora.ProcessadoraPagamento;
import com.toolschallenge.api.gestao.pagamento.service.processadora.ProcessadoraPagamentoResponse;

import lombok.Builder;
import lombok.Getter;

@ExtendWith(MockitoExtension.class)
public class RealizaPagamentoServiceTest {

    @InjectMocks
    private RealizaPagamentoService service;

    @Mock
    private ProcessadoraPagamento integration;

    @Mock
    private PagamentoRepository repository;

    @Spy
    private ComunicaPagamentoRequestMapper requestMapper = getMapper(ComunicaPagamentoRequestMapper.class);

    @Spy
    private PagamentoResponseMapper responseMapper = getMapper(PagamentoResponseMapper.class);

    @Test
    public void deveRealizarPagamentoComSucesso() {
        final PagamentoRequest request = Fixture.make(PagamentoRequest.builder()).build();
        final MockProcessadoraPagamentoResponse integrationResponse = Fixture
            .make(MockProcessadoraPagamentoResponse.builder()).build();
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

        final PagamentoResponse resultado = service.pagar(request);

        verify(integration).comunicaPagamento(any());
        verify(requestMapper).apply(any());
        verify(responseMapper).apply(any());
        verify(repository).save(any());

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

    @Test
    public void naoDeveSalvarPagamentoQuandoErroNaProcessador(){
        final PagamentoRequest request = Fixture.make(PagamentoRequest.builder()).build();

        doThrow(new ServerErrorException(randomAlphanumeric(16))).when(integration).comunicaPagamento(any());

        assertThrows(ServerErrorException.class, () -> service.pagar(request));

        verify(integration).comunicaPagamento(any());
        verify(requestMapper).apply(any());

        verifyNoInteractions(repository);
        verifyNoInteractions(responseMapper);

    }

    @Getter
    @Builder
    static class MockProcessadoraPagamentoResponse implements ProcessadoraPagamentoResponse {

        private final Long nsu;
        private final Long codigoAutorizacao;
        private final StatusPagamento status;

    }

}