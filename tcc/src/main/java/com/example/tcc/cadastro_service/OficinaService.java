package com.example.tcc.cadastro_service;

import com.example.tcc.cadastro_model.OficinaModel; // Importe o modelo correto
import com.example.tcc.cadastro_repository.OficinaRepository; // Importe o repositório correto
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OficinaService {

    private final OficinaRepository oficinaRepository;
    private static final int RAIO_TERRA_KM = 6371;

    public OficinaService(OficinaRepository oficinaRepository) {
        this.oficinaRepository = oficinaRepository;
    }

    /**
     * Busca oficinas próximas ao ponto do usuário.
     */
    public List<OficinaModel> buscarProximas(double latUsuario, double lonUsuario, double raioKm) {

        List<OficinaModel> todasOficinas = oficinaRepository.findAll();

        return todasOficinas.stream()
                .filter(oficina -> {
                    // OBTENDO AS COORDENADAS DO OBJETO EMBUTIDO
                    if (oficina.getCoordenadas() == null) {
                        return false;
                    }

                    double latOficina = oficina.getCoordenadas().getLatitude();
                    double lonOficina = oficina.getCoordenadas().getLongitude();

                    // Verifica se as coordenadas são válidas antes de calcular
                    if (latOficina == 0.0 && lonOficina == 0.0) {
                        return false;
                    }

                    double distancia = calcularDistanciaHaversine(
                            latUsuario, lonUsuario,
                            latOficina, lonOficina
                    );

                    return distancia <= raioKm;
                })
                .collect(Collectors.toList());
    }

    /**
     * Fórmula de Haversine para cálculo de distância.
     */
    private double calcularDistanciaHaversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RAIO_TERRA_KM * c;
    }
}