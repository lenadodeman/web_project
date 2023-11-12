package com.example.web_project.api.service;

import com.example.web_project.api.model.Event;
import com.example.web_project.api.model.Tag;
import com.example.web_project.api.repository.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final TagService tagService;

    public EventService(EventRepository eventRepository, TagService tagService) {
        this.eventRepository = eventRepository;
        this.tagService = tagService;
    }

    public Event getEvent(final long id_event)
    {
        return eventRepository.findById(id_event).orElseThrow(IllegalArgumentException::new);
    }

    public Iterable<Event> getAllEvents()
    {
        return eventRepository.findAll();
    }

    public Event addEvent(Event event)
    {
        return eventRepository.save(event);
    }

    @Transactional
    public void deleteEvent(final long id_event)
    {
        eventRepository.deleteById(id_event);
    }

    @Transactional
    public void associateTagToEvent(final long id_event, final long id_tag)
    {
        Event event = this.getEvent(id_event);
        Tag tag = tagService.getTag(id_tag);

        event.getTags().add(tag);
        tag.getEvents().add(event);

    }

    @Transactional
    public void unlinkTagFromEvent(final long id_event, final long id_tag)
    {
        Event event = this.getEvent(id_event);
        Tag tag = tagService.getTag(id_tag);

        event.getTags().remove(tag);
        tag.getEvents().remove(event);

    }



}
