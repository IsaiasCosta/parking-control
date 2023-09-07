package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.ParkingSpotService;


import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")

public class ParkingSpotController {
    // ponto de injeção do service no controller

    final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto) {
        if (parkingSpotService.existsByLicencePlateCar(parkingSpotDto.getLicencePlateCar())) { // Conferindo se existe registro para a placa do carro
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License Plate Car is already in use!");
        }
        if (parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())) {// Conferindo se existe registro para a vaga  do carro
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot is already in use !");
        }
        if (parkingSpotService.existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())) {// Conferindo se existe registro para o apartamento e bloco  do carro
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot already registered for this apartment /block");
        }

        var parkingSpotModel = new ParkingSpotModel();
        Calendar parkinCalendar = Calendar.getInstance();
        ZoneId sp = ZoneId.of("America/Sao_Paulo");
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
        parkingSpotModel.setRegistrationDate(LocalDateTime.ofInstant(parkinCalendar.toInstant(), sp));
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
    }
    // lISTANDO VAGAS DO ESTACIONAMENTO

    @GetMapping // REcuperando a lista do estacionamento
    public ResponseEntity<List<ParkingSpotModel>> getAllParkingSpots() {
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll());
    }

    @GetMapping("/{id}")// Recuperando a informação com o id

    public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value = "id") UUID id) {
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOneParkingSpot(@PathVariable(value = "id") UUID id) {
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
        }
        parkingSpotService.delete(parkingSpotModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Parking Spot delete");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") UUID id,
                                                    @RequestBody @Valid ParkingSpotDto parkingSpotDto) {
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
        }
        // realizar em dua formas
        // 1º FORMA
        /*var parkingSpotModel = parkingSpotModelOptional.get();
        parkingSpotModel.setParkingSpotNumber(parkingSpotDto.getParkingSpotNumber());
        parkingSpotModel.setLicencePlateCar(parkingSpotDto.getLicencePlateCar());
        parkingSpotModel.setBrandCar(parkingSpotDto.getBrandCar());
        parkingSpotModel.setModelCar(parkingSpotDto.getModelCar());
        parkingSpotModel.setColorCar(parkingSpotDto.getColorCar());
        parkingSpotModel.setResponsibleName(parkingSpotDto.getResponsibleName());
        parkingSpotModel.setApartment(parkingSpotDto.getApartment());
        parkingSpotModel.setBlock(parkingSpotDto.getBlock());*/


        //2º FORMA
        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDto,parkingSpotModel);
        parkingSpotModel.setId(parkingSpotModelOptional.get().getId());// setar o ID que  não sera alterado
        parkingSpotModel.setRegistrationDate(parkingSpotModelOptional.get().getRegistrationDate());  //setar a data  ID que não sera alterado
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel));
    }

}
