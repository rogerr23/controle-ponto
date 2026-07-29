package rogerr.com.controleponto.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rogerr.com.controleponto.dtos.AutenticarFuncionarioRequest;
import rogerr.com.controleponto.dtos.AutenticarFuncionarioResponse;
import rogerr.com.controleponto.dtos.FuncionarioRequest;
import rogerr.com.controleponto.dtos.FuncionarioResponse;
import rogerr.com.controleponto.services.FuncionarioService;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/funcionario")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping("cadastrar")
    public ResponseEntity<FuncionarioResponse> post(@RequestBody @Valid FuncionarioRequest request) throws Exception {
        return ResponseEntity.status(201).body(funcionarioService.cadastrar(request));
    }

    @GetMapping("consultar")
    public ResponseEntity<List<FuncionarioResponse>> getAll() throws Exception {
        return ResponseEntity.status(200).body(funcionarioService.consultar());
    }

    @GetMapping("/obter/{id}")
    public ResponseEntity<FuncionarioResponse> getById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.status(200).body(funcionarioService.consultarPorId(id));
    }

    @PostMapping("autenticar")
    public ResponseEntity<AutenticarFuncionarioResponse> auth(@RequestBody @Valid AutenticarFuncionarioRequest request) throws Exception {
        return ResponseEntity.status(200).body(funcionarioService.autenticar(request));
    }

}
