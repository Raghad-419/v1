package com.example.capston3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MaintenanceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(20) not null ")
    @NotEmpty(message = "Empty expert name")
    private String expert_name;
    @Column(columnDefinition = "DATE")
    private LocalDate requestDate = LocalDate.now();  // Date when the maintenance request was created by owner
    @Positive(message = "Price must be a positive number!")
    @Column(columnDefinition = "DOUBLE not null")
    private Double totalPrice;
    @Column(columnDefinition = "varchar(10) default 'Pending'")
    @Pattern(regexp = "Pending|Completed", message = "Status must be 'Pending' or 'Completed'")
    private String status= "Pending";

    //Relations

    @ManyToOne
    @JsonIgnore
    private Owner owner;  // The owner who made the maintenance request

    @ManyToOne
    @JsonIgnore
    private MaintenanceExpert expert;

    @OneToOne
    @JsonIgnore
    private Motorcycle motorcycle;
}
