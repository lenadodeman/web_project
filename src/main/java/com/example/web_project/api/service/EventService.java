package com.example.web_project.api.service;

import com.example.web_project.api.dto.EventDTO;
import com.example.web_project.api.dto.SerieDTO;
import com.example.web_project.api.mapper.EventMapper;
import com.example.web_project.api.model.Event;
import com.example.web_project.api.model.Serie;
import com.example.web_project.api.model.SerieAccess;
import com.example.web_project.api.model.Tag;
import com.example.web_project.api.repository.EventRepository;
import com.example.web_project.api.repository.SerieAcessRepository;
import com.example.web_project.api.repository.SerieRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final TagService tagService;

    private final UserService userService;
    private final SerieService serieService;

    private final SerieAcessRepository serieAcessRepository;
    private final SerieRepository serieRepository;

    private final EventMapper eventMapper = EventMapper.INSTANCE;

    public EventService(EventRepository eventRepository, TagService tagService, UserService userService, SerieService serieService, SerieAcessRepository serieAcessRepository, SerieRepository serieRepository) {
        this.eventRepository = eventRepository;
        this.tagService = tagService;
        this.userService = userService;
        this.serieService = serieService;
        this.serieAcessRepository = serieAcessRepository;
        this.serieRepository = serieRepository;
    }

    public Event findEventById(final long eventId)
    {
        return eventRepository.findById(eventId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Event not found with id: " + eventId)
                );
    }

    public EventDTO getEvent(long eventId){
        return eventMapper.toDTO(findEventById(eventId));
    }

    public List<EventDTO> getAllEvents()
    {
        List<Event> eventList = eventRepository.findAll();
        return eventMapper.toDTOList(eventList);
    }

    public EventDTO addEvent(EventDTO eventDTO) throws AccessDeniedException {

        //Test de l'existence de l'event
        if(eventRepository.existsById(eventDTO.getId()))
        {
            throw new IllegalStateException("Event with id " + eventDTO.getId() + " already exists");
        }

        Serie serie = serieService.findSerieById(eventDTO.getSerie().getId());

        Long userId = userService.getCurrentUserId();
        SerieDTO serieDTO = serieService.getTimeSerie(serie.getId());
        SerieAccess access = serieAcessRepository.findBySerieIdAndUserId(serie.getId(), userId)
                .orElseThrow(() -> new AccessDeniedException("No access to this serie"));

        if (!access.getAccessType().equals("WRITE")) {
            throw new AccessDeniedException("Insufficient permissions");
        }

        //Mapping de l'event en objet de type domain
        Event event = eventMapper.toDomain(eventDTO);
        event.setSerie(serieService.findSerieById(eventDTO.getSerie().getId()));

        //Sauvegarde de l'event
        Event eventSaved = eventRepository.save(event);

        serie.getEventList().add(eventSaved);
        serieRepository.save(serie);

        return eventMapper.toDTO(event);
    }


    public void deleteEvent(final long eventId)
    {

        if(!eventRepository.existsById(eventId))
        {
            throw new EntityNotFoundException("Event not found with id: " + eventId);
        }

        Event event = this.findEventById(eventId);
        for (Tag tag : event.getTags()) {
            tag.getEvents().remove(event);
        }
        event.getTags().clear();
        eventRepository.save(event);
        eventRepository.deleteById(eventId);
    }

    @Transactional
    public EventDTO updateEvent(EventDTO eventDTO)
    {
        //Test de l'existence de l'event
        if(!eventRepository.existsById(eventDTO.getId()))
        {
            throw new EntityNotFoundException("Event not found with id: " + eventDTO.getId());
        }
        //Mapping en objet de type domain
        Event updateEvent = eventMapper.toDomain(eventDTO);

        //Mise à jour de l'event
        return eventMapper.toDTO(eventRepository.save(updateEvent));
    }

    @Transactional
    public void associateTagToEvent(final long id_event, final long id_tag)
    {

        Event event = this.findEventById(id_event);

        Tag tag = tagService.findTagById(id_tag);

        event.getTags().add(tag);
        tag.getEvents().add(event);
    }

    @Transactional
    public void unlinkTagFromEvent(final long id_event, final long id_tag)
    {
        Event event = this.findEventById(id_event);
        Tag tag = tagService.findTagById(id_tag);

        event.getTags().remove(tag);
        tag.getEvents().remove(event);
    }



}
