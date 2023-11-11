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


    @GetMapping("/event/{id_event}")
    public Object getEvent(@PathVariable("id_event") long id_event)
    {
        Event event = eventService.getEvent(id_event);
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

    @DeleteMapping("/deleteEvent/{id_event}")
    public void deleteEvent(@PathVariable("id_event") long id_event)
    {
        eventService.deleteEvent(id_event);

    }




}
