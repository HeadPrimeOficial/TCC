package com.example.tcc.cadastro_model;


import jakarta.persistence.Embeddable;
// Remova todos os imports do Lombok: import lombok.Data, AllArgsConstructor, NoArgsConstructor

@Embeddable // Mantemos esta anotação para o JPA!
public class Coordenadas {

    private double latitude;
    private double longitude;

    // Construtor Vazio (Obrigatório para o JPA/Hibernate)
    public Coordenadas() {}

    // Construtor Completo (Opcional, mas útil para o NominatimService)
    public Coordenadas(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // GETTERS (Obrigatórios para o MapaController e OficinaService)
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    // SETTERS (Obrigatórios para o NominatimService)
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}