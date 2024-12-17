package com.example.capston3.Service;


import com.example.capston3.ApiResponse.ApiException;
import com.example.capston3.DTO.MaintenanceRequestDTO;
import com.example.capston3.InDTO.MaintenanceRequestDTO_In;
import com.example.capston3.Model.MaintenanceRequest;
import com.example.capston3.Model.Motorcycle;
import com.example.capston3.Model.Owner;
import com.example.capston3.Repository.MaintenanceRequestRepository;
import com.example.capston3.Repository.MotorcycleRepository;
import com.example.capston3.Repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceRequestService {

    private final MaintenanceRequestRepository maintenanceRequestRepository;
    private final OwnerRepository ownerRepository;
    private final MotorcycleRepository motorcycleRepository;



    public List<MaintenanceRequestDTO> getAllMaintenanceRequest(){

        List<MaintenanceRequest> maintenanceRequests = maintenanceRequestRepository.findAll();

        List<MaintenanceRequestDTO> maintenanceRequestDTOS = new ArrayList<>();

        for(MaintenanceRequest maintenanceRequest : maintenanceRequests){
            MaintenanceRequestDTO motorcycleDTOS = new MaintenanceRequestDTO(maintenanceRequest.getRequestDate(),maintenanceRequest.getTotalPrice(),maintenanceRequest.getExpert_name(),maintenanceRequest.getStatus(),maintenanceRequest.getPickupDate());
            maintenanceRequestDTOS.add(motorcycleDTOS);
        }
        return maintenanceRequestDTOS;
    }

    public void addMaintenanceRequest(MaintenanceRequestDTO_In maintenanceRequestDTO_in){

        Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(maintenanceRequestDTO_in.getMotorcycle_id());
        if(motorcycle == null){
            throw new ApiException("Motorcycle not found");
        }

        Owner owner = ownerRepository.findOwnerById(maintenanceRequestDTO_in.getOwner_id());
        if(owner == null)
            throw new ApiException("Owner not found");

        MaintenanceRequest maintenanceRequest = new MaintenanceRequest(maintenanceRequestDTO_in.getExpert_name(),maintenanceRequestDTO_in.getPickupDate(),owner);
        maintenanceRequestRepository.save(maintenanceRequest);

    }


    public void updateMaintenanceRequest(Integer maintenanceRequest_id, MaintenanceRequestDTO_In maintenanceRequestDTO_in){

        MaintenanceRequest maintenanceRequest = maintenanceRequestRepository.findMaintenanceRequestById(maintenanceRequest_id);

        if(maintenanceRequest ==null)
            throw new ApiException("MaintenanceRequest not found!");

        maintenanceRequest.setExpert_name(maintenanceRequestDTO_in.getExpert_name());


        if (maintenanceRequestDTO_in.getOwner_id() != null) {
            Owner owner = ownerRepository.findById(maintenanceRequestDTO_in.getOwner_id())
                    .orElseThrow(() -> new ApiException("Owner not found !"));
            maintenanceRequest.setOwner(owner);
        }

        maintenanceRequestRepository.save(maintenanceRequest);
    }

    public void deleteMaintenanceRequest(Integer maintenanceRequest_id ){

        MaintenanceRequest maintenanceRequest = maintenanceRequestRepository.findMaintenanceRequestById(maintenanceRequest_id);
        if(maintenanceRequest == null)
            throw new ApiException("MaintenanceRequest not found!");

        maintenanceRequestRepository.delete(maintenanceRequest);

    }




















}
