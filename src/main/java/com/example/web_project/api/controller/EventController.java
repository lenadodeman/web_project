package com.example.web_project.api.controller;

import com.example.web_project.api.dto.EventDTO;
import com.example.web_project.api.mapper.EventMapper;
import com.example.web_project.api.model.Event;
import com.example.web_project.api.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    private final EventMapper eventMapper = EventMapper.INSTANCE;

    public EventController(EventService eventService) {
        this.eventService = eventService;

    }


    @GetMapping("/{id_event}")
    public ResponseEntity<EventDTO> getEvent(@PathVariable("id_event") long id_event)
    {
        EventDTO eventDTO = eventService.getEvent(id_event);
        return ResponseEntity.ok(eventDTO);
    }

    @GetMapping()
    public String getAllEvents(Model model)
    {
        List<EventDTO> events = eventService.getAllEvents();
        model.addAttribute("events", events);
        return "events";
    }

    @PostMapping()
    public EventDTO addEvent(@RequestBody EventDTO eventDTO)
    {
        return eventService.addEvent(eventDTO);
    }

    @DeleteMapping("/{id_event}")
    public ResponseEntity<HttpStatus> deleteEvent(@PathVariable("id_event") long id_event)
    {
        eventService.deleteEvent(id_event);
        return new ResponseEntity<>(HttpStatus.OK);

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
    public EventDTO updateEvent(@RequestBody EventDTO updateEvent)
    {
        return eventService.updateEvent(updateEvent);
    }





}
