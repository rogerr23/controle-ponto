package rogerr.com.controleponto.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;
import rogerr.com.controleponto.dtos.HistoricoRequest;
import rogerr.com.controleponto.dtos.HistoricoResponse;
import rogerr.com.controleponto.entities.Funcionario;
import rogerr.com.controleponto.entities.Historico;
import rogerr.com.controleponto.entities.Operacao;
import rogerr.com.controleponto.repositories.FuncionarioRepository;
import rogerr.com.controleponto.repositories.HistoricoRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class HistoricoServiceImplTest {

    private HistoricoServiceImpl service;
    private FuncionarioRepository funcionarioRepository;
    private HistoricoRepository historicoRepository;

    @BeforeEach
    void setUp() {
        funcionarioRepository = mock(FuncionarioRepository.class);
        historicoRepository = mock(HistoricoRepository.class);
        service = new HistoricoServiceImpl();
        ReflectionTestUtils.setField(service, "funcionarioRepository", funcionarioRepository);
        ReflectionTestUtils.setField(service, "historicoRepository", historicoRepository);
        ReflectionTestUtils.setField(service, "modelMapper", new ModelMapper());
    }

    @Test
    void cadastrarDeveRegistrarOperacaoParaFuncionarioExistente() {
        UUID funcionarioId = UUID.randomUUID();
        Funcionario funcionario = new Funcionario(funcionarioId, "Roger Santos", "roger@exemplo.com", "Senha@123", null);
        HistoricoRequest request = new HistoricoRequest(funcionarioId, "EXPEDIENTE_INICIO", "-23.5505", "-46.6333");
        when(funcionarioRepository.findById(funcionarioId)).thenReturn(Optional.of(funcionario));

        HistoricoResponse response = service.cadastrar(request);

        assertNotNull(response.getId());
        assertEquals(funcionarioId, response.getFuncionario_id());
        assertEquals("EXPEDIENTE_INICIO", response.getOperacao());
        verify(historicoRepository).save(any(Historico.class));
    }

    @Test
    void cadastrarDeveRejeitarFuncionarioInexistente() {
        UUID funcionarioId = UUID.randomUUID();
        HistoricoRequest request = new HistoricoRequest(funcionarioId, "EXPEDIENTE_INICIO", "-23.5505", "-46.6333");
        when(funcionarioRepository.findById(funcionarioId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.cadastrar(request));

        assertEquals("O ID informado não pertence a um funcionário cadastrado no sistema.", exception.getMessage());
    }

    @Test
    void consultarPorFuncionarioDeveMapearHistoricos() {
        UUID funcionarioId = UUID.randomUUID();
        Funcionario funcionario = new Funcionario(funcionarioId, "Roger Santos", "roger@exemplo.com", "Senha@123", null);
        Historico historico = new Historico(UUID.randomUUID(), funcionario, Operacao.EXPEDIENTE_FIM, new Date(), "-23.5505", "-46.6333");
        when(funcionarioRepository.findById(funcionarioId)).thenReturn(Optional.of(funcionario));
        when(historicoRepository.findByHistoricoPorIdDeFuncionario(funcionarioId)).thenReturn(List.of(historico));

        List<HistoricoResponse> response = service.consultarPorIdDeFuncionario(funcionarioId);

        assertEquals(1, response.size());
        assertEquals(funcionarioId, response.getFirst().getFuncionario_id());
        assertEquals("EXPEDIENTE_FIM", response.getFirst().getOperacao());
    }
}
