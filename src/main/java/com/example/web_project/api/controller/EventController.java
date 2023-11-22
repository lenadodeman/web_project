package com.example.web_project.api.controller;

import com.example.web_project.api.model.Event;
import com.example.web_project.api.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;


    public EventController(EventService eventService) {
        this.eventService = eventService;

    }


    @GetMapping("/{id_event}")
    public Object getEvent(@PathVariable("id_event") long id_event)
    {
        Event event = eventService.getEvent(id_event);
        return ResponseEntity.ok(event);
    }

    @GetMapping()
    public String getAllEvents(Model model)
    {
        Iterable<Event> events = eventService.getAllEvents();
        model.addAttribute("events", events);
        return "events";
    }

    @PostMapping()
    public Event addEvent(@RequestBody Event event)
    {
        return eventService.addEvent(event);
    }

    @DeleteMapping("/{id_event}")
    public void deleteEvent(@PathVariable("id_event") long id_event)
    {
        eventService.deleteEvent(id_event);
    }

    @PostMapping("/{id_event}/associateTagToEvent/{id_tag}")
    public ResponseEntity<?> associateTagToEvent(@PathVariable long id_event, @PathVariable long id_tag) {
        eventService.associateTagToEvent(id_event, id_tag);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id_event}/unlinkTagFromEvent/{id_tag}")
    public ResponseEntity<?> unlinkTagFromEvent(@PathVariable long id_event, @PathVariable long id_tag) {
        eventService.unlinkTagFromEvent(id_event, id_tag);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public Event updateEvent(@RequestBody Event updateEvent)
    {
        return eventService.updateEvent(updateEvent);
    }





}
