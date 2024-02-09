package com.toolschallenge.api.gestao.pagamento.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toolschallenge.api.gestao.pagamento.request.PagamentoRequest;
import com.toolschallenge.api.gestao.pagamento.response.PagamentoResponse;
import com.toolschallenge.api.gestao.pagamento.service.ConsultaPagamentoService;
import com.toolschallenge.api.gestao.pagamento.service.EstornaPagamentoService;
import com.toolschallenge.api.gestao.pagamento.service.RealizaPagamentoService;

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
    private final ConsultaPagamentoService consultaPagamentoService;
    private final EstornaPagamentoService estornaPagamentoService;

    @Operation(summary = "Realiza um pagamento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Os valores informados são inválidos para o request.",
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Falha inesperada.",
            content = @Content) })
    @PostMapping
    public PagamentoResponse pagamento(@Valid @RequestBody final PagamentoRequest request) {
        return realizaPagamentoService.pagar(request);
    }

    @Operation(summary = "Consulta um pagamento pelo identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Os valores informados são inválidos para o request.",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Nenhum resultado encontrado.",
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Falha inesperada.",
            content = @Content) })
    @GetMapping("/{id}")
    public PagamentoResponse consultaPagamento(@PathVariable final String id) {
        return consultaPagamentoService.consultar(id);
    }

    @Operation(summary = "Consulta todos pagamento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Os valores informados são inválidos para o request.",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Nenhum resultado encontrado.",
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Falha inesperada.",
            content = @Content) })
    @GetMapping()
    public Page<PagamentoResponse> consultaPagamento(@PageableDefault final Pageable pageble) {
        return consultaPagamentoService.consultar(pageble);
    }

    @Operation(summary = "Realiza o estorno de um pagamento pelo identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Os valores informados são inválidos para o request.",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Nenhum resultado encontrado.",
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Falha inesperada.",
            content = @Content) })
    @PatchMapping("/{id}/estorno")
    public PagamentoResponse estornoPagamento(@PathVariable final String id) {
        return estornaPagamentoService.estornar(id);
    }
}
