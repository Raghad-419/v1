package com.example.capston3.Repository;

import com.example.capston3.Model.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotorcycleRepository extends JpaRepository<Motorcycle,Integer> {
    Motorcycle findMotorcycleById(Integer id);
}