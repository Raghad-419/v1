package com.example.capston3.Service;


import com.example.capston3.ApiResponse.ApiException;
import com.example.capston3.DTO.RentingRequestOutDTO;
import com.example.capston3.InDTO.RentingRequestDTO_In;
import com.example.capston3.Model.Motorcycle;
import com.example.capston3.Model.Renting;
import com.example.capston3.Model.RentingRequest;
import com.example.capston3.Model.User;
import com.example.capston3.Repository.MotorcycleRepository;
import com.example.capston3.Repository.RentingRepository;
import com.example.capston3.Repository.RentingRequestRepository;
import com.example.capston3.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentingRequestService {
    private final RentingRequestRepository rentingRequestRepository;
    private final RentingRepository rentingRepository;
    private final UserRepository userRepository;
    private final MotorcycleRepository motorcycleRepository;


    public List<RentingRequestOutDTO> getAllRentingRequests() {
        List<RentingRequest> rentingRequests = rentingRequestRepository.findAll();
        List<RentingRequestOutDTO> rentingRequestOutDTOs = new ArrayList<>();
        for (RentingRequest rentingRequest : rentingRequests) {
            rentingRequestOutDTOs.add(new RentingRequestOutDTO(rentingRequest.getRequestDate(),rentingRequest.getStartDate(),rentingRequest.getEndDate(),rentingRequest.getTotalCost()));
        }
        return rentingRequestOutDTOs;
    }

//    public void addRentingRequest(RentingRequestDTO_In rentingRequestInDTO) {
//        Renting renting = rentingRepository.findRentingById(rentingRequestInDTO.getRenting_id());
//        if(renting == null) {
//            throw new ApiException("Renting not found");
//        }
//        User user = userRepository.findUserById(rentingRequestInDTO.getUser_id());
//        if(user == null) {
//            throw new ApiException("User not found");
//        }
//        Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(renting.getMotorcycleId());
//        if(motorcycle.getIsAvailable()){
//            RentingRequest rentingRequest = new RentingRequest(rentingRequestInDTO.getStartDate(),rentingRequestInDTO.getEndDate(),user,renting,motorcycle.getId());
//            rentingRequestRepository.save(rentingRequest);
//            rentingRepository.save(renting);
//
//        }
//
//
//        rentingRepository.save(renting);
//    }

    public void addRentingRequest(RentingRequestDTO_In rentingRequestDTOIn) {
        // Step 1: Validate input dates
        if (rentingRequestDTOIn.getStartDate().isAfter(rentingRequestDTOIn.getEndDate())) {
            throw new ApiException("Start date cannot be after end date!");
        }
        if (rentingRequestDTOIn.getStartDate().isBefore(LocalDate.now())) {
            throw new ApiException("Start date must be today or in the future!");
        }

        // Step 2: Check if the Renting offer exists
        Renting renting = rentingRepository.findRentingById(rentingRequestDTOIn.getRenting_id());
                if(renting ==null){
                 throw  new ApiException("Renting offer not found!");}

        // Step 3: Check for existing rentals on the selected motorcycle
        boolean isRented = rentingRepository.existsByMotorcycleAndDateRange(
                renting.getMotorcycleId(), rentingRequestDTOIn.getStartDate(), rentingRequestDTOIn.getEndDate()
        );
        if (isRented) {
            throw new ApiException("The motorcycle is not available for the requested dates!");
        }

        // Step 4: Fetch the User
        User user = userRepository.findUserById(rentingRequestDTOIn.getUser_id());
        if(user ==null){
            throw  new ApiException("User not found!");}
        // Step 5: Create and save RentingRequest
        RentingRequest rentingRequest = new RentingRequest();
        rentingRequest.setUser(user);
        rentingRequest.setRenting(renting);
        rentingRequest.setStartDate(rentingRequestDTOIn.getStartDate());
        rentingRequest.setEndDate(rentingRequestDTOIn.getEndDate());

        // Calculate total cost based on price per day
        int totalCost = calculateTotalCost(renting.getPricePerDay(), rentingRequestDTOIn.getStartDate(), rentingRequestDTOIn.getEndDate());
        rentingRequest.setTotalCost(totalCost);

        rentingRequestRepository.save(rentingRequest);

        // Return success message
        System.out.println("Your rental request has been successfully created! Total cost: " + totalCost);
    }

    // Helper method to calculate total cost
    private int calculateTotalCost(Double pricePerDay, LocalDate startDate, LocalDate endDate) {
        long days = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;
        return (int) (days * pricePerDay);
    }


    public void updateRentingRequest(Integer rentingRequest_id,RentingRequestDTO_In rentingRequestInDTO) {
        Renting renting = rentingRepository.findRentingById(rentingRequestInDTO.getRenting_id());
        if(renting == null) {
            throw new ApiException("Renting not found");
        }
        RentingRequest rentingRequest = rentingRequestRepository.findRentingRequestById(rentingRequest_id);
        if(rentingRequest == null) {
            throw new ApiException("Renting Request not found");
        }
        User user = userRepository.findUserById(rentingRequestInDTO.getUser_id());
        if(user == null) {
            throw new ApiException("User not found");
        }
        rentingRequest.setStartDate(rentingRequestInDTO.getStartDate());
        rentingRequest.setEndDate(rentingRequestInDTO.getEndDate());
        rentingRequest.setRenting(renting);
        rentingRequest.setUser(user);
        rentingRequestRepository.save(rentingRequest);
    }

    public void deleteRentingRequest(Integer rentingRequest_id) {
        RentingRequest rentingRequest = rentingRequestRepository.findRentingRequestById(rentingRequest_id);
        long hoursUntilStart = Duration.between(LocalDateTime.now(), rentingRequest.getStartDate()).toHours();
        if (hoursUntilStart > 48){
            Renting renting = rentingRepository.findRentingById(rentingRequest_id);
            if(renting != null) {
                renting.setRentingRequest(null);
                rentingRepository.save(renting);
            }
                rentingRequestRepository.deleteById(rentingRequest_id);

        }

    }

}
