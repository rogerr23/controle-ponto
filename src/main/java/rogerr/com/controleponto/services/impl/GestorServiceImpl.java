package rogerr.com.controleponto.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rogerr.com.controleponto.dtos.AutenticarGestorRequest;
import rogerr.com.controleponto.dtos.AutenticarGestorResponse;
import rogerr.com.controleponto.dtos.GestorRequest;
import rogerr.com.controleponto.dtos.GestorResponse;
import rogerr.com.controleponto.entities.Gestor;
import rogerr.com.controleponto.repositories.GestorRepository;
import rogerr.com.controleponto.services.GestorService;

import java.util.UUID;

@Service
public class GestorServiceImpl implements GestorService {

    @Autowired
    private GestorRepository gestorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public GestorResponse cadastrar(GestorRequest request) {

        Gestor gestor = modelMapper.map(request, Gestor.class);
        gestor.setId(UUID.randomUUID());

        gestorRepository.save(gestor);

        return modelMapper.map(gestor, GestorResponse.class);
    }

    @Override
    public GestorResponse consultarPorId(UUID id) {

        Gestor gestor = gestorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("O ID informado não foi encontrado."));

        return modelMapper.map(gestor, GestorResponse.class);
    }

    @Override
    public AutenticarGestorResponse autenticar(AutenticarGestorRequest request) {

        Gestor gestor = gestorRepository.findByEmailAndSenha(request.getEmail(), request.getSenha());

        if (gestor != null) {

            return modelMapper.map(gestor, AutenticarGestorResponse.class);
        } else {
            throw new IllegalArgumentException(
                    "Email ou senha informados estão inválidos. Por favor, tente novamente.");
        }
    }
}
