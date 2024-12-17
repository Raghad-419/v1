package com.example.capston3.Controller;

import com.example.capston3.ApiResponse.ApiResponse;
import com.example.capston3.Model.Event;
import com.example.capston3.Service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/event")
public class EventController {
    private final EventService eventService;

    @GetMapping("/get")
    public ResponseEntity getEvents(){
        return ResponseEntity.status(200).body(eventService.getEvents());
    }

    @PostMapping("/add/{companyId}")
    public ResponseEntity addEvent(@PathVariable  Integer companyId, @RequestBody @Valid Event event){
        eventService.addEvent(companyId,event);
        return ResponseEntity.status(200).body(new ApiResponse("Event added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateEvent(@PathVariable Integer id, @RequestBody @Valid Event event){
        eventService.updateEvent(id,event);
        return ResponseEntity.status(200).body(new ApiResponse("Event updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEvent(@PathVariable Integer id){
        eventService.deleteEvent(id);
        return ResponseEntity.status(200).body(new ApiResponse("Event deleted"));

    }
}
