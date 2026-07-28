package rogerr.com.controleponto.services;

import rogerr.com.controleponto.dtos.AutenticarGestorRequest;
import rogerr.com.controleponto.dtos.AutenticarGestorResponse;
import rogerr.com.controleponto.dtos.GestorRequest;
import rogerr.com.controleponto.dtos.GestorResponse;

import java.util.UUID;

public interface GestorService {

    GestorResponse cadastrar (GestorRequest request);
    GestorResponse consultarPorId(UUID id);
    AutenticarGestorResponse autenticar(AutenticarGestorRequest request);

}
