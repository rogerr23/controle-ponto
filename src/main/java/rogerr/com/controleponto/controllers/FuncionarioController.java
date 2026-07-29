package rogerr.com.controleponto.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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


@Tag(
        name = "Funcionários",
        description = "Cadastro, consulta e autenticação de funcionários"
)
@RestController
@RequestMapping("/api/funcionario")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @Operation(
            summary = "Cadastrar funcionário",
            description = "Cadastra um novo funcionário no sistema."
    )
    @PostMapping("cadastrar")
    public ResponseEntity<FuncionarioResponse> post(@RequestBody @Valid FuncionarioRequest request) throws Exception {
        return ResponseEntity.status(201).body(funcionarioService.cadastrar(request));
    }

    @Operation(
            summary = "Consultar funcionários",
            description = "Retorna todos os funcionários cadastrados."
    )
    @GetMapping("consultar")
    public ResponseEntity<List<FuncionarioResponse>> getAll() throws Exception {
        return ResponseEntity.status(200).body(funcionarioService.consultar());
    }

    @Operation(
            summary = "Consultar funcionário por ID",
            description = "Retorna o funcionário correspondente ao ID informado."
    )
    @GetMapping("/obter/{id}")
    public ResponseEntity<FuncionarioResponse> getById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.status(200).body(funcionarioService.consultarPorId(id));
    }

    @Operation(
            summary = "Autenticar funcionário",
            description = "Autentica um funcionário por meio de suas credenciais."
    )
    @PostMapping("autenticar")
    public ResponseEntity<AutenticarFuncionarioResponse> auth(@RequestBody @Valid AutenticarFuncionarioRequest request) throws Exception {
        return ResponseEntity.status(200).body(funcionarioService.autenticar(request));
    }

}
