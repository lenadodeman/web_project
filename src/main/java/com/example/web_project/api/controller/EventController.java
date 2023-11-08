package com.example.web_project.api.controller;

import com.example.web_project.api.model.Event;
import com.example.web_project.api.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }


    @GetMapping("/events/{idEvent}")
    public Object getEvent(@PathVariable("idEvent") long idEvent)
    {
        Event event = eventService.getEvent(idEvent);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/events")
    public Iterable<Event> getAllEvents()
    {
        return eventService.getAllEvents();
    }

    @PostMapping("/addEvent")
    public Event addEvent(@RequestBody Event event)
    {
        return eventService.addEvent(event);
    }

    @DeleteMapping("/deleteEvent/{idEvent}")
    public void deleteEvent(@PathVariable("idEvent") long idEvent)
    {
        eventService.deleteEvent(idEvent);

    }




}
