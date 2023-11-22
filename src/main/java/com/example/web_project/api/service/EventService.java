package com.example.web_project.api.service;

import com.example.web_project.api.dto.EventDTO;
import com.example.web_project.api.mapper.EventMapper;
import com.example.web_project.api.model.Event;
import com.example.web_project.api.model.Tag;
import com.example.web_project.api.repository.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final TagService tagService;

    private final SerieService serieService;
    private final EventMapper eventMapper = EventMapper.INSTANCE;

    public EventService(EventRepository eventRepository, TagService tagService, SerieService serieService) {
        this.eventRepository = eventRepository;
        this.tagService = tagService;
        this.serieService = serieService;
    }

    public EventDTO getEvent(final long id_event)
    {
        return eventMapper.eventToEventDTO(eventRepository.findById(id_event).orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + id_event)));
    }

    public List<EventDTO> getAllEvents()
    {
        return eventMapper.eventsToEventsDTO(eventRepository.findAll());
    }

    public EventDTO addEvent(EventDTO eventDTO)
    {

        Event event = eventMapper.eventDTOToEvent(eventDTO);
        event.setSerie(serieService.getTimeSerie(eventDTO.getSerie().getId()));
        if(eventRepository.existsById(event.getId()) && event.getId() != 0)
        {
            throw new IllegalStateException("Event with id " + event.getId() + " already exists");
        }

        return eventMapper.eventToEventDTO(eventRepository.save(event));
    }


    public void deleteEvent(final long id_event)
    {
        if(!eventRepository.existsById(id_event))
        {
            throw new EntityNotFoundException("Event not found with id: " + id_event);
        }
        eventRepository.deleteById(id_event);
    }

    @Transactional
    public void associateTagToEvent(final long id_event, final long id_tag)
    {
        Event event = eventMapper.eventDTOToEvent(this.getEvent(id_event));
        Tag tag = tagService.getTag(id_tag);
        event.getTags().add(tag);
        tag.getEvents().add(event);

    }

    @Transactional
    public void unlinkTagFromEvent(final long id_event, final long id_tag)
    {
        Event event = eventMapper.eventDTOToEvent(this.getEvent(id_event));
        Tag tag = tagService.getTag(id_tag);

        event.getTags().remove(tag);
        tag.getEvents().remove(event);

    }

    @Transactional
    public EventDTO updateEvent(EventDTO event)
    {
        Event updateEvent = eventMapper.eventDTOToEvent(event);
        if(!eventRepository.existsById(updateEvent.getId()))
        {
            throw new EntityNotFoundException("Event not found with id: " + updateEvent.getId());
        }
        updateEvent.setDate(updateEvent.getDate());
        updateEvent.setValueEvent(updateEvent.getValueEvent());
        updateEvent.setComment(updateEvent.getComment());
        return eventMapper.eventToEventDTO(eventRepository.save(updateEvent));
    }



}
