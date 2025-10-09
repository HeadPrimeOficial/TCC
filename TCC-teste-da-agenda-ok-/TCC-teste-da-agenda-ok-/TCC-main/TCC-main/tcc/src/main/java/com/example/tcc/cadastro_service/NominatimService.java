package com.example.tcc.cadastro_service;

import com.example.tcc.cadastro_model.Coordenadas;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class NominatimService {

    private final RestTemplate restTemplate;

    @Value("${nominatim.geocoding.url}")
    private String nominatimUrl;

    public NominatimService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Converte um endereço em Latitude e Longitude usando o Nominatim do OpenStreetMap.
     */
    public Coordenadas obterCoordenadas(String cepOuEndereco) {

        // Parâmetros: format=json, q=o endereço, limit=1
        String urlCompleta = nominatimUrl + "?format=json&q={endereco}&limit=1";

        try {
            // A resposta é uma lista de resultados.
            List<Map<String, String>> resposta = restTemplate.getForObject(
                    urlCompleta,
                    List.class,
                    cepOuEndereco
            );

            if (resposta == null || resposta.isEmpty()) {
                throw new RuntimeException("Endereço não encontrado pelo Nominatim.");
            }

            // O primeiro resultado tem as coordenadas como strings "lat" e "lon"
            Map<String, String> result = resposta.get(0);

            // Extrai as coordenadas e as converte para Double
            double lat = Double.parseDouble(result.get("lat"));
            double lng = Double.parseDouble(result.get("lon"));

            return new Coordenadas(lat, lng);

        } catch (Exception e) {
            System.err.println("Erro ao obter coordenadas do Nominatim: " + e.getMessage());
            throw new RuntimeException("Falha ao processar a requisição de Geocodificação.", e);
        }
    }
}