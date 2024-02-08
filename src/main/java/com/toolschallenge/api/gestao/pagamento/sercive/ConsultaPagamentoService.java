package com.toolschallenge.api.gestao.pagamento.sercive;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.toolschallenge.api.gestao.pagamento.domain.enuns.ErrorType;
import com.toolschallenge.api.gestao.pagamento.exception.ClientErrorException;
import com.toolschallenge.api.gestao.pagamento.mapper.PagamentoResponseMapStruct;
import com.toolschallenge.api.gestao.pagamento.repository.PagamentoRepository;
import com.toolschallenge.api.gestao.pagamento.response.PagamentoResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultaPagamentoService {

    private final PagamentoRepository repository;
    private final PagamentoResponseMapStruct pagamentoResponseMapStruct;

    public PagamentoResponse consultar(final Long id) {
        log.info("Realizando consulta de pagamento pelo identificador: {}", id);

        var pagametnoEntity = repository.findById(id).orElseThrow(() -> {
            throw new ClientErrorException(ErrorType.NOT_FOUND, "Nenhum registro encontrado");
        });

        return pagamentoResponseMapStruct.apply(pagametnoEntity);
    }

    public Page<PagamentoResponse> consultar(final Pageable pageable) {
        log.info("Realizando consulta de todos pagamentos");

        var pagamentoEntities = repository.findAll(pageable);

        return pagamentoEntities.map(pagamentoResponseMapStruct::apply);
    }

}
