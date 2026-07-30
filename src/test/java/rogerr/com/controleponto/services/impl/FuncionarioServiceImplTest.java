package rogerr.com.controleponto.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;
import rogerr.com.controleponto.dtos.AutenticarFuncionarioRequest;
import rogerr.com.controleponto.dtos.FuncionarioRequest;
import rogerr.com.controleponto.dtos.FuncionarioResponse;
import rogerr.com.controleponto.entities.Funcionario;
import rogerr.com.controleponto.repositories.FuncionarioRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FuncionarioServiceImplTest {

    private FuncionarioServiceImpl service;
    private FuncionarioRepository funcionarioRepository;

    @BeforeEach
    void setUp() {
        funcionarioRepository = mock(FuncionarioRepository.class);
        service = new FuncionarioServiceImpl();
        ReflectionTestUtils.setField(service, "funcionarioRepository", funcionarioRepository);
        ReflectionTestUtils.setField(service, "modelMapper", new ModelMapper());
    }

    @Test
    void cadastrarDeveNormalizarDadosESalvarFuncionario() {
        FuncionarioRequest request = new FuncionarioRequest("  Roger Santos  ", "  ROGER@EXEMPLO.COM ", "Senha@123");
        when(funcionarioRepository.existsByEmailIgnoreCase("roger@exemplo.com")).thenReturn(false);

        FuncionarioResponse response = service.cadastrar(request);

        assertNotNull(response.getId());
        assertEquals("Roger Santos", response.getNome());
        assertEquals("roger@exemplo.com", response.getEmail());
        verify(funcionarioRepository).save(any(Funcionario.class));
    }

    @Test
    void cadastrarDeveImpedirEmailDuplicado() {
        FuncionarioRequest request = new FuncionarioRequest("Roger Santos", "roger@exemplo.com", "Senha@123");
        when(funcionarioRepository.existsByEmailIgnoreCase("roger@exemplo.com")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.cadastrar(request));

        assertEquals("Já existe um funcionário cadastrado com este email.", exception.getMessage());
        verify(funcionarioRepository, never()).save(any());
    }

    @Test
    void consultarPorIdDeveRetornarFuncionarioExistente() {
        UUID id = UUID.randomUUID();
        Funcionario funcionario = new Funcionario(id, "Roger Santos", "roger@exemplo.com", "Senha@123", null);
        when(funcionarioRepository.findById(id)).thenReturn(Optional.of(funcionario));

        FuncionarioResponse response = service.consultarPorId(id);

        assertEquals(id, response.getId());
        assertEquals("Roger Santos", response.getNome());
    }

    @Test
    void autenticarDeveRejeitarCredenciaisInvalidas() {
        AutenticarFuncionarioRequest request = new AutenticarFuncionarioRequest("roger@exemplo.com", "SenhaErrada@1");
        when(funcionarioRepository.findByEmailAndSenha(request.getEmail(), request.getSenha())).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.autenticar(request));

        assertEquals("Email ou senha informados estão inválidos. Por favor, tente novamente.", exception.getMessage());
    }
}
