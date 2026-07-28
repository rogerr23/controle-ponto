package rogerr.com.controleponto.services;

import rogerr.com.controleponto.dtos.HistoricoRequest;
import rogerr.com.controleponto.dtos.HistoricoResponse;

import java.util.List;
import java.util.UUID;

public interface HistoricoService {

    HistoricoResponse cadastrar(HistoricoRequest request);
    List<HistoricoResponse> consultar();
    List<HistoricoResponse> consultarPorIdDeFuncionario(UUID funcionario_id);
    List<HistoricoResponse> consultarPorIdDeFuncionarioHoje(UUID funcionario_id);
}
