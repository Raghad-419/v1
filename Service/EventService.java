package com.example.capston3.Service;

import com.example.capston3.ApiResponse.ApiException;
import com.example.capston3.DTO.EventDTO;
import com.example.capston3.Model.Company;
import com.example.capston3.Model.Event;
import com.example.capston3.Repository.CompanyRepository;
import com.example.capston3.Repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final CompanyRepository companyRepository;
    public List<EventDTO> getEvents(){
        List<Event> events = eventRepository.findAll();
        List<EventDTO> eventDTOS = new ArrayList<>();

        for(Event event:events){
            EventDTO eventDTO = new EventDTO(event.getName(),event.getLocation(),event.getDetails(),event.getDate());
            eventDTOS.add(eventDTO);
        }
        return eventDTOS;
    }


    public void addEvent(Integer companyId ,Event event){
        Company company = companyRepository.findCompanyById(companyId);
        if(company== null){
            throw new ApiException("Company not found");
        }
        event.setCompany(company);
        eventRepository.save(event);
    }

    public void updateEvent(Integer id, Event event){
        Event event1 = eventRepository.findEventById(id);
        if(event1==null){
            throw new ApiException("Event not found");
        }
        event1.setDate(event.getDate());
        event1.setDetails(event.getDetails());
        event1.setName(event.getName());
        event1.setLocation(event.getLocation());
    }


    public void deleteEvent(Integer id){
        Event event = eventRepository.findEventById(id);
        if(event ==null){
            throw new ApiException("Event not found");
        }
        eventRepository.delete(event);
    }

}
