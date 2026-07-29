package rogerr.com.controleponto.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rogerr.com.controleponto.dtos.AutenticarGestorRequest;
import rogerr.com.controleponto.dtos.AutenticarGestorResponse;
import rogerr.com.controleponto.dtos.GestorRequest;
import rogerr.com.controleponto.dtos.GestorResponse;
import rogerr.com.controleponto.services.GestorService;

import java.util.UUID;

@Tag(
        name = "Gestores",
        description = "Cadastro, consulta e autenticação de gestores"
)
@RestController
@RequestMapping("/api/gestor")
public class GestorController {

    @Autowired
    private GestorService gestorService;

    @Operation(
            summary = "Cadastrar gestor",
            description = "Cadastra um novo gestor no sistema."
    )
    @PostMapping("cadastrar")
    public ResponseEntity<GestorResponse> post(@RequestBody @Valid GestorRequest request) throws Exception {
        return ResponseEntity.status(201).body(gestorService.cadastrar(request));
    }

    @Operation(
            summary = "Consultar gestor por ID",
            description = "Retorna o gestor correspondente ao ID informado."
    )
    @GetMapping("/obter/{id}")
    public ResponseEntity<GestorResponse> getById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.status(200).body(gestorService.consultarPorId(id));
    }

    @Operation(
            summary = "Autenticar gestor",
            description = "Autentica um gestor por meio de suas credenciais."
    )
    @PostMapping("autenticar")
    public ResponseEntity<AutenticarGestorResponse> auth(@RequestBody @Valid AutenticarGestorRequest request) throws Exception {
        return ResponseEntity.status(200).body(gestorService.autenticar(request));
    }

}
