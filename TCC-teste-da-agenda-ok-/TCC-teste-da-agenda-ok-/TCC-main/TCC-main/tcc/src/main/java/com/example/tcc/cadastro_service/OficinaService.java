package com.example.tcc.cadastro_service;

import com.example.tcc.cadastro_model.OficinaModel;
import com.example.tcc.cadastro_repository.OficinaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OficinaService {

    private final OficinaRepository oficinaRepository;
    // Raio Médio da Terra em KM (essencial para a fórmula Haversine)
    private static final int RAIO_TERRA_KM = 6371;

    public OficinaService(OficinaRepository oficinaRepository) {
        this.oficinaRepository = oficinaRepository;
    }

    /**
     * Busca todas as oficinas e retorna apenas aquelas dentro de um raio de KM.
     * * @param latUsuario Latitude do endereço do usuário.
     * @param lonUsuario Longitude do endereço do usuário.
     * @param raioKm Raio máximo de busca em quilômetros.
     * @return Lista de OficinaModel dentro do raio.
     */
    public List<OficinaModel> buscarProximas(double latUsuario, double lonUsuario, double raioKm) {

        // 1. Busca todas as oficinas no MySQL
        List<OficinaModel> todasOficinas = oficinaRepository.findAll();

        // 2. Filtra as oficinas que estão dentro do raio de busca
        return todasOficinas.stream()
                .filter(oficina -> {
                    // Ignora oficinas que não foram geocodificadas
                    if (oficina.getLatitude() == null || oficina.getLongitude() == null) {
                        return false;
                    }

                    // 3. Calcula a distância entre o usuário e a oficina
                    double distancia = calcularDistanciaHaversine(
                            latUsuario, lonUsuario,
                            oficina.getLatitude(), oficina.getLongitude()
                    );

                    // 4. Retorna TRUE se a distância for menor ou igual ao raio
                    return distancia <= raioKm;
                })
                .collect(Collectors.toList());
    }

    /**
     * Implementação da Fórmula de Haversine para calcular a distância entre
     * dois pontos de Lat/Lon na Terra (em Km).
     */
    private double calcularDistanciaHaversine(double lat1, double lon1, double lat2, double lon2) {

        // Converte graus para radianos
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Retorna a distância em Quilômetros
        return RAIO_TERRA_KM * c;
    }
}