package com.example.BackendLogin.service;

import com.example.BackendLogin.model.Events;
import com.example.BackendLogin.repository.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventsService {

    @Autowired
    private EventsRepository eventsRepository;

    public Events saveEvent(Events event) {
        return eventsRepository.save(event);
    }

    public List<Events> getEventsByUserId(String userId) {
        return eventsRepository.findByUserId(userId);
    }
}