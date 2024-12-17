package com.example.capston3.Repository;

import com.example.capston3.Model.RentingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentingRequestRepository extends JpaRepository<RentingRequest,Integer> {
    RentingRequest findRentingRequestById(Integer id);
}
