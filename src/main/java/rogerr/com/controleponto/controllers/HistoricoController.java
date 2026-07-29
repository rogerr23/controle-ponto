package rogerr.com.controleponto.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rogerr.com.controleponto.dtos.HistoricoRequest;
import rogerr.com.controleponto.dtos.HistoricoResponse;
import rogerr.com.controleponto.services.HistoricoService;

import java.util.List;
import java.util.UUID;


@Tag(
        name = "Históricos",
        description = "Registro e consulta das operações de ponto"
)
@RestController
@RequestMapping("/api/historico")
public class HistoricoController {

    @Autowired
    private HistoricoService historicoService;

    @Operation(
            summary = "Registrar operação de ponto",
            description = "Registra uma nova operação de ponto para um funcionário."
    )
    @PostMapping
    public ResponseEntity<HistoricoResponse> post(@RequestBody @Valid HistoricoRequest request) throws Exception {
        return ResponseEntity.status(201).body(historicoService.cadastrar(request));
    }

    @Operation(
            summary = "Consultar históricos",
            description = "Retorna todos os registros de operações de ponto."
    )
    @GetMapping("consultar")
    public ResponseEntity<List<HistoricoResponse>> getAll() throws Exception {
        return ResponseEntity.status(200).body(historicoService.consultar());
    }

    @Operation(
            summary = "Consultar histórico por funcionário",
            description = "Retorna o histórico de operações do funcionário informado."
    )
    @GetMapping("obterPorIdDeFuncionario/{funcionario_id}")
    public ResponseEntity<List<HistoricoResponse>> getByIdDeFuncionario(@PathVariable UUID funcionario_id) throws Exception {
        return ResponseEntity.status(200).body(historicoService.consultarPorIdDeFuncionario(funcionario_id));
    }

    @Operation(
            summary = "Consultar histórico diário",
            description = "Retorna as operações realizadas hoje pelo funcionário informado."
    )
    @GetMapping("obterPorIdDeFuncionarioHoje/{funcionario_id}")
    public ResponseEntity<List<HistoricoResponse>> getByIdDeFuncionarioHoje(@PathVariable UUID funcionario_id) throws Exception {
        return ResponseEntity.status(200).body(historicoService.consultarPorIdDeFuncionarioHoje(funcionario_id));
    }
}
