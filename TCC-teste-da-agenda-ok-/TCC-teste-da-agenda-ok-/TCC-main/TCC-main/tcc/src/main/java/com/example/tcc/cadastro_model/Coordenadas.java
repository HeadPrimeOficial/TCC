package com.example.tcc.cadastro_model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // Construtor sem argumentos (essencial para o Spring)
@AllArgsConstructor // Construtor com todos os argumentos (útil para criar o objeto)
public class Coordenadas {

    private double latitude;
    private double longitude;

    public Coordenadas(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
