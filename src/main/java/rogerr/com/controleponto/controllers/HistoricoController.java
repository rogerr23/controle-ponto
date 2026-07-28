package rogerr.com.controleponto.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rogerr.com.controleponto.dtos.HistoricoRequest;
import rogerr.com.controleponto.dtos.HistoricoResponse;
import rogerr.com.controleponto.services.FuncionarioService;
import rogerr.com.controleponto.services.HistoricoService;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/historico")
public class HistoricoController {

    @Autowired
    private HistoricoService historicoService;

    @PostMapping
    public ResponseEntity<HistoricoResponse> post(@RequestBody @Valid HistoricoRequest request) throws Exception {
        return ResponseEntity.status(201).body(historicoService.cadastrar(request));
    }

    @GetMapping("consultar")
    public ResponseEntity<List<HistoricoResponse>> getAll() throws Exception {
        return ResponseEntity.status(200).body(historicoService.consultar());
    }

    @GetMapping("obterPorIdDeFuncionario/{funcionario_id}")
    public ResponseEntity<List<HistoricoResponse>> getByIdDeFuncionario(@PathVariable UUID funcionario_id) throws Exception {
        return ResponseEntity.status(200).body(historicoService.consultarPorIdDeFuncionario(funcionario_id));
    }

    @GetMapping("obterPorIdDeFuncionarioHoje/{funcionario_id}")
    public ResponseEntity<List<HistoricoResponse>> getByIdDeFuncionarioHoje(@PathVariable UUID funcionario_id) throws Exception {
        return ResponseEntity.status(200).body(historicoService.consultarPorIdDeFuncionarioHoje(funcionario_id));
    }
}
