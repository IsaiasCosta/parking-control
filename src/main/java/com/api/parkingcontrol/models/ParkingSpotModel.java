package com.api.parkingcontrol.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name= "TB_PARKING_SPOT")// Nome da Tabela
public class ParkingSpotModel implements Serializable {
    private  static  final long serialVersionUID =1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)// ID gerado automaticamente
    private UUID id; //identificar unico e universal
    @Column(nullable = false,unique = true,length  =10) // não null, unico, tamanho 10
    private String parkingSpotNumber; // numero da vaga
    @Column(nullable = false,unique = true,length  =10) // não null, unico, tamanho 7
    private String licencePlateCar; // placa do veiculo
    @Column(nullable = false,length  = 70) //não null, tamanho 70
    private String brandCar; // marca do veiculo
    @Column(nullable = false,length  = 70) //não null, tamanho 70
    private String modelCar; // modelo do veiculo
    @Column(nullable = false,length  = 70) //não null, tamanho 70
    private String colorCar; // cor do veiculo
    @Column(nullable = false) // não null
    private LocalDateTime registrationDate; // data do registro do veigulo dentro do sistema
    @Column(nullable = false,length  =130) // não null, unico, tamanho 130
    private String responsibleName;// Nome do responsavel
    @Column(nullable = false,length  =30) //não null, tamanho 30
    private String apartment;//apartamento do responsavel pelo veiculo
    @Column(nullable = false,length  =30) //não null, tamanho 30
    private String block; // bloco do apartamento

  // Metodos Gettes e Setttes
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getParkingSpotNumber() {
        return parkingSpotNumber;
    }

    public void setParkingSpotNumber(String parkingSpotNumber) {
        this.parkingSpotNumber = parkingSpotNumber;
    }

    public String getLicencePlateCar() {
        return licencePlateCar;
    }

    public void setLicencePlateCar(String licencePlateCar) {
        this.licencePlateCar = licencePlateCar;
    }

    public String getBrandCar() {
        return brandCar;
    }

    public void setBrandCar(String brandCar) {
        this.brandCar = brandCar;
    }

    public String getModelCar() {
        return modelCar;
    }

    public void setModelCar(String modelCar) {
        this.modelCar = modelCar;
    }

    public String getColorCar() {
        return colorCar;
    }

    public void setColorCar(String colorCar) {
        this.colorCar = colorCar;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getResponsibleName() {
        return responsibleName;
    }

    public void setResponsibleName(String responsibleName) {
        this.responsibleName = responsibleName;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }
}
