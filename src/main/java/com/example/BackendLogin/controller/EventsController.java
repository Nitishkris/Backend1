package com.example.BackendLogin.controller;

import com.example.BackendLogin.model.Events;
import com.example.BackendLogin.service.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@CrossOrigin(origins = "http://localhost:3000") // Allow React frontend
public class EventsController {

    @Autowired
    private EventsService eventsService;

    @PostMapping
    public Events createEvent(@RequestBody Events event) {
        System.out.println("Received Event: " + event);
        return eventsService.saveEvent(event);
    }

    @GetMapping("/user/{userId}")
    public List<Events> getEventsByUser(@PathVariable String userId) {
        return eventsService.getEventsByUserId(userId);
    }
}