package com.example.capston3.Repository;

import com.example.capston3.Model.EventRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRegistrationRepository extends JpaRepository<EventRegistration,Integer> {
    EventRegistration findEventRegistrationById(Integer id);
}