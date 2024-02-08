package com.toolschallenge.api.gestao.pagamento.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toolschallenge.api.gestao.pagamento.request.PagamentoRequest;
import com.toolschallenge.api.gestao.pagamento.response.PagamentoResponse;
import com.toolschallenge.api.gestao.pagamento.sercive.RealizaPagamentoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pagamento")
public class PagamentoController{

    private final RealizaPagamentoService realizaPagamentoService;

    @Operation(summary = "Realiza um pagamento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Os valores informados são inválidos para o request.",
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Falha inesperada.",
            content = @Content) })
    @PostMapping
    public PagamentoResponse pagamento(@Valid @RequestBody final PagamentoRequest request) {
        return realizaPagamentoService.pagamento(request);
    }
}