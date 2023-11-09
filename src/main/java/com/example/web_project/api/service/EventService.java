package com.example.web_project.api.service;

import com.example.web_project.api.model.Event;
import com.example.web_project.api.model.Tag;
import com.example.web_project.api.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event getEvent(final long eventId)
    {
        return eventRepository.findById(eventId).orElseThrow(IllegalArgumentException::new);
    }

    public Iterable<Event> getAllEvents()
    {
        return eventRepository.findAll();
    }

    public Event addEvent(Event event)
    {
        return eventRepository.save(event);
    }

    public void deleteEvent(final long eventId)
    {
        eventRepository.deleteById(eventId);
    }


//    public void associateTag(final long idEvent, Tag tag)
//    {
//        eventRepository.findById(idEvent).ifPresent(event -> {
//            event.getTagsList().add(tag);
//        });
//
//    }
//
//    public void unlinkTag(final long idEvent, Tag tag)
//    {
//        Optional<Event> eventOptional = eventRepository.findById(idEvent);
//        if(eventOptional.isPresent()) {
//            Event event = eventOptional.get();
//            event.getTagsList().remove(tag); //Faut il mettre l'id du tag ou juste tag ?
//        }
//
//    }



}
