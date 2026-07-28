package rogerr.com.controleponto.services;

import rogerr.com.controleponto.dtos.AutenticarFuncionarioRequest;
import rogerr.com.controleponto.dtos.AutenticarFuncionarioResponse;
import rogerr.com.controleponto.dtos.FuncionarioRequest;
import rogerr.com.controleponto.dtos.FuncionarioResponse;

import java.util.List;
import java.util.UUID;

public interface FuncionarioService {

    FuncionarioResponse cadastrar(FuncionarioRequest request);
    FuncionarioResponse consultarPorId(UUID id);
    List<FuncionarioResponse> consultar();
    AutenticarFuncionarioResponse autenticar (AutenticarFuncionarioRequest request);

}
