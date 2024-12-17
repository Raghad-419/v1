package com.example.capston3.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(columnDefinition = "date")
    private LocalDate purchaseDate = LocalDate.now();

    @ManyToOne
    @JsonIgnore
    private User user;

    @OneToOne
    @MapsId
    @JsonIgnore
    private Motorcycle motorcycle;


}
