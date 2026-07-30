package rogerr.com.controleponto.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;
import rogerr.com.controleponto.dtos.AutenticarGestorRequest;
import rogerr.com.controleponto.dtos.AutenticarGestorResponse;
import rogerr.com.controleponto.dtos.GestorRequest;
import rogerr.com.controleponto.dtos.GestorResponse;
import rogerr.com.controleponto.entities.Gestor;
import rogerr.com.controleponto.repositories.GestorRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GestorServiceImplTest {

    private GestorServiceImpl service;
    private GestorRepository gestorRepository;

    @BeforeEach
    void setUp() {
        gestorRepository = mock(GestorRepository.class);
        service = new GestorServiceImpl();
        ReflectionTestUtils.setField(service, "gestorRepository", gestorRepository);
        ReflectionTestUtils.setField(service, "modelMapper", new ModelMapper());
    }

    @Test
    void cadastrarDeveGerarIdESalvarGestor() {
        GestorRequest request = new GestorRequest("Ana Gestora", "ana@exemplo.com", "Senha@123");

        GestorResponse response = service.cadastrar(request);

        assertNotNull(response.getId());
        assertEquals("Ana Gestora", response.getNome());
        assertEquals("ana@exemplo.com", response.getEmail());
        verify(gestorRepository).save(any(Gestor.class));
    }

    @Test
    void consultarPorIdDeveRetornarGestorExistente() {
        UUID id = UUID.randomUUID();
        Gestor gestor = new Gestor(id, "Ana Gestora", "ana@exemplo.com", "Senha@123");
        when(gestorRepository.findById(id)).thenReturn(Optional.of(gestor));

        GestorResponse response = service.consultarPorId(id);

        assertEquals(id, response.getId());
        assertEquals("Ana Gestora", response.getNome());
    }

    @Test
    void consultarPorIdDeveLancarErroQuandoGestorNaoExiste() {
        UUID id = UUID.randomUUID();
        when(gestorRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.consultarPorId(id));

        assertEquals("O ID informado não foi encontrado.", exception.getMessage());
    }

    @Test
    void autenticarDeveRetornarGestorQuandoCredenciaisSaoValidas() {
        UUID id = UUID.randomUUID();
        AutenticarGestorRequest request = new AutenticarGestorRequest("ana@exemplo.com", "Senha@123");
        Gestor gestor = new Gestor(id, "Ana Gestora", request.getEmail(), request.getSenha());
        when(gestorRepository.findByEmailAndSenha(request.getEmail(), request.getSenha())).thenReturn(gestor);

        AutenticarGestorResponse response = service.autenticar(request);

        assertEquals(id, response.getId());
        assertEquals("Ana Gestora", response.getNome());
    }

    @Test
    void autenticarDeveRejeitarCredenciaisInvalidas() {
        AutenticarGestorRequest request = new AutenticarGestorRequest("ana@exemplo.com", "SenhaErrada@1");
        when(gestorRepository.findByEmailAndSenha(request.getEmail(), request.getSenha())).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.autenticar(request));

        assertEquals("Email ou senha informados estão inválidos. Por favor, tente novamente.", exception.getMessage());
    }
}
