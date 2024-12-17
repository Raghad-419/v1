package com.example.capston3.InDTO;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RentingRequestDTO_In {


    private Integer renting_id;

    @FutureOrPresent(message = "Start date must be in the present or future")
    private LocalDate startDate;

    @Future(message = "End date must be in the future")
    private LocalDate endDate;



}