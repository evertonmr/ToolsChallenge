package com.toolschallenge.api.gestao.pagamento.service;

import org.springframework.stereotype.Service;

import com.toolschallenge.api.gestao.pagamento.domain.enums.ErrorType;
import com.toolschallenge.api.gestao.pagamento.domain.enums.StatusPagamento;
import com.toolschallenge.api.gestao.pagamento.entity.PagamentoEntity;
import com.toolschallenge.api.gestao.pagamento.exception.ClientErrorException;
import com.toolschallenge.api.gestao.pagamento.mapper.PagamentoResponseMapper;
import com.toolschallenge.api.gestao.pagamento.repository.PagamentoRepository;
import com.toolschallenge.api.gestao.pagamento.response.PagamentoResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EstornaPagamentoService {

    private final PagamentoRepository repository;
    private final PagamentoResponseMapper responseMapStruct;

    public PagamentoResponse estornar(final String id) {
        log.info("Estornando pagamento para identificador: {}", id);

        var pagamento = repository.findByIdTransacao(id).orElseThrow(() -> {
            throw new ClientErrorException(ErrorType.NOT_FOUND, "Nenhum registro encontrado");
        });

        registraEstorno(pagamento);

        return responseMapStruct.apply(pagamento);
    }

    private void registraEstorno(final PagamentoEntity pagamento) {
        pagamento.setStatus(StatusPagamento.CANCELADO);

        repository.save(pagamento);

    }
}
