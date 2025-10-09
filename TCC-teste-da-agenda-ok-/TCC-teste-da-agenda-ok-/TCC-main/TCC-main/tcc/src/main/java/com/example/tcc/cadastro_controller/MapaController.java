package com.example.tcc.cadastro_controller;

import com.example.tcc.cadastro_model.Coordenadas;
import com.example.tcc.cadastro_service.NominatimService; // <--- NOVO NOME DO SERVIÇO
import com.example.tcc.cadastro_model.OficinaModel;
import com.example.tcc.cadastro_service.OficinaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mapa")
public class MapaController {

    private final NominatimService nominatimService; // Usando o serviço gratuito
    private final OficinaService oficinaService;

    public MapaController(NominatimService nominatimService, OficinaService oficinaService) {
        this.nominatimService = nominatimService;
        this.oficinaService = oficinaService;
    }

    // Endpoint: GET /api/mapa/proximas?endereco=Rua+Exemplo,+123&raio=5
    @GetMapping("/proximas")
    public List<OficinaModel> buscarOficinasProximas(
            @RequestParam String endereco, // Endereço (ou CEP) digitado pelo usuário
            @RequestParam(defaultValue = "10") double raio) { // Raio de busca em KM (padrão 10 km)

        // 1. CONVERTER ENDEREÇO DO USUÁRIO EM COORDENADAS
        Coordenadas coordsUsuario = nominatimService.obterCoordenadas(endereco);

        // 2. BUSCAR OFICINAS PRÓXIMAS (a lógica Haversine está no Serviço)
        return oficinaService.buscarProximas(
                coordsUsuario.getLatitude(),
                coordsUsuario.getLongitude(),
                raio
        );
    }
}
