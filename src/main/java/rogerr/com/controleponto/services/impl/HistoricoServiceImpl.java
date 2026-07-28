package rogerr.com.controleponto.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rogerr.com.controleponto.dtos.HistoricoRequest;
import rogerr.com.controleponto.dtos.HistoricoResponse;
import rogerr.com.controleponto.entities.Funcionario;
import rogerr.com.controleponto.entities.Historico;
import rogerr.com.controleponto.entities.Operacao;
import rogerr.com.controleponto.repositories.FuncionarioRepository;
import rogerr.com.controleponto.repositories.HistoricoRepository;
import rogerr.com.controleponto.services.HistoricoService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class HistoricoServiceImpl implements HistoricoService {

    @Autowired
    private HistoricoRepository historicoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public HistoricoResponse cadastrar(HistoricoRequest request) {

        Funcionario funcionario = funcionarioRepository.findById(request.getFuncionario_id())
                .orElseThrow(() -> new IllegalArgumentException(
                        "O ID informado não pertence a um funcionário cadastrado no sistema."));

        Historico historico = modelMapper.map(request, Historico.class);
        historico.setId(UUID.randomUUID());
        historico.setFuncionario(funcionario);
        historico.setOperacao(Operacao.valueOf(request.getOperacao()));
        historico.setDataHoraOperacao(new Date());

        historicoRepository.save(historico);

        HistoricoResponse response = modelMapper.map(historico, HistoricoResponse.class);
        response.setFuncionario_id(funcionario.getId());

        return response;

    }

    @Override
    public List<HistoricoResponse> consultar() {

        List<Historico> historicos = historicoRepository.findAll();

        List<HistoricoResponse> response = new ArrayList<>();

        Integer indice = 0;
        for (Historico historico : historicos) {
            HistoricoResponse historicoLoop = new HistoricoResponse();
            historicoLoop.setId(historico.getId());
            historicoLoop.setFuncionario_id(historico.getFuncionario().getId());
            historicoLoop.setOperacao(historico.getOperacao().toString());
            historicoLoop.setLatitude(historico.getLatitude());
            historicoLoop.setLongitude(historico.getLongitude());
            historicoLoop.setDataHoraOperacao(historico.getDataHoraOperacao());

            response.add(historicoLoop);
            indice++;
        }

        return response;
    }

    @Override
    public List<HistoricoResponse> consultarPorIdDeFuncionario(UUID funcionario_id) {

        funcionarioRepository.findById(funcionario_id)
                .orElseThrow(() -> new IllegalArgumentException("O ID informado não pertence a um funcionário cadastrado no sistema."));

        List<Historico> historicos = historicoRepository.findByHistoricoPorIdDeFuncionario(funcionario_id);

        List<HistoricoResponse> response = new ArrayList<>();

        Integer indice = 0;
        for (Historico historico : historicos) {
            HistoricoResponse historicoLoop = new HistoricoResponse();
            historicoLoop.setId(historico.getId());
            historicoLoop.setFuncionario_id(historico.getFuncionario().getId());
            historicoLoop.setOperacao(historico.getOperacao().toString());
            historicoLoop.setLatitude(historico.getLatitude());
            historicoLoop.setLongitude(historico.getLongitude());
            historicoLoop.setDataHoraOperacao(historico.getDataHoraOperacao());

            response.add(historicoLoop);
            indice++;
        }

        return response;
    }

    @Override
    public List<HistoricoResponse> consultarPorIdDeFuncionarioHoje(UUID funcionario_id) {

        funcionarioRepository.findById(funcionario_id)
                .orElseThrow(() -> new IllegalArgumentException("O ID informado não pertence a um funcionário cadastrado no sistema."));

        List<Historico> historicos = historicoRepository.findByHistoricoPorIdDeFuncionarioHoje(funcionario_id);

        List<HistoricoResponse> response = new ArrayList<>();

        Integer indice = 0;
        for (Historico historico : historicos) {
            HistoricoResponse historicoLoop = new HistoricoResponse();
            historicoLoop.setId(historico.getId());
            historicoLoop.setFuncionario_id(historico.getFuncionario().getId());
            historicoLoop.setOperacao(historico.getOperacao().toString());
            historicoLoop.setLatitude(historico.getLatitude());
            historicoLoop.setLongitude(historico.getLongitude());
            historicoLoop.setDataHoraOperacao(historico.getDataHoraOperacao());

            response.add(historicoLoop);
            indice++;
        }

        return response;
    }



}
