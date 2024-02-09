package com.toolschallenge.api.gestao.pagamento.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mapstruct.factory.Mappers.getMapper;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.toolschallenge.api.gestao.pagamento.domain.enums.StatusPagamento;
import com.toolschallenge.api.gestao.pagamento.entity.PagamentoEntity;
import com.toolschallenge.api.gestao.pagamento.exception.ClientErrorException;
import com.toolschallenge.api.gestao.pagamento.fixture.Fixture;
import com.toolschallenge.api.gestao.pagamento.mapper.PagamentoResponseMapper;
import com.toolschallenge.api.gestao.pagamento.repository.PagamentoRepository;
import com.toolschallenge.api.gestao.pagamento.response.PagamentoResponse;

@ExtendWith(MockitoExtension.class)
public class EstornaPagamentoServiceTest {

    @InjectMocks
    private EstornaPagamentoService service;

    @Mock
    private PagamentoRepository repository;

    @Spy
    private PagamentoResponseMapper responseMapStruct = getMapper(PagamentoResponseMapper.class);

    @Captor
    private ArgumentCaptor<PagamentoEntity> entityArgumentCaptor;

    @Test
    public void deveEstornarPagamentoDeAcordoComId(){
        final PagamentoEntity entity = Fixture.make(new PagamentoEntity());

        when(repository.findByIdTransacao(any())).thenReturn(Optional.of(entity));

        final PagamentoResponse resultado = service.estornar(entity.getIdTransacao());

        verify(repository).findByIdTransacao(entity.getIdTransacao());
        verify(responseMapStruct).apply(entity);
        verify(repository).save(entityArgumentCaptor.capture());

        assertEquals(entityArgumentCaptor.getValue(), entity);
        assertEquals(StatusPagamento.CANCELADO, resultado.getTransacao().getStatus());
        assertEquals(StatusPagamento.CANCELADO, entityArgumentCaptor.getValue().getStatus());

        assertResultado(entity, resultado);

    }

    @Test
    public void deveRetornarErroQuandoNaoEncontradoPeloId() {
        final String id = UUID.randomUUID().toString();

        when(repository.findByIdTransacao(any())).thenReturn(Optional.empty());

        assertThrows(ClientErrorException.class, () -> service.estornar(id));

        verify(repository).findByIdTransacao(id);
        verifyNoInteractions(responseMapStruct);
    }

    private void assertResultado(final PagamentoEntity entity, final PagamentoResponse resultado) {
        assertEquals(resultado.getTransacao().getId(), entity.getIdTransacao());
        assertEquals(resultado.getTransacao().getCartao(), entity.getCartao());
        assertEquals(resultado.getTransacao().getDescricao().getEstabelecimento(), entity.getEstabelecimento());
        assertEquals(resultado.getTransacao().getDescricao().getDataHora(), entity.getDataHora());
        assertEquals(resultado.getTransacao().getDescricao().getValor(), entity.getValor());
        assertEquals(resultado.getTransacao().getFormaPagamento().getParcelas(), entity.getParcelas());
        assertEquals(resultado.getTransacao().getFormaPagamento().getTipo(), entity.getTipo());
        assertEquals(resultado.getTransacao().getDescricao().getCodigoAutorizacao(), entity.getCodigoAutorizacao());
        assertEquals(resultado.getTransacao().getDescricao().getNsu(), entity.getNsu());
        assertEquals(resultado.getTransacao().getStatus(), entity.getStatus());
    }

}