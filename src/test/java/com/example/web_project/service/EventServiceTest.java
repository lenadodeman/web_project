package com.example.web_project.service;

import com.example.web_project.api.dto.EventDTO;
import com.example.web_project.api.dto.SerieDTO;
import com.example.web_project.api.mapper.EventMapper;
import com.example.web_project.api.model.Event;
import com.example.web_project.api.model.Serie;
import com.example.web_project.api.model.Tag;
import com.example.web_project.api.repository.EventRepository;
import com.example.web_project.api.service.EventService;
import com.example.web_project.api.service.SerieService;
import com.example.web_project.api.service.TagService;
import jakarta.persistence.EntityNotFoundException;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;
    @Mock
    private TagService tagService;
    @Mock
    private SerieService serieService;
    @Mock
    private EventMapper eventMapper;

    @InjectMocks
    private EventService eventService;

    @Test
    public void testFindEventById() {
        Event event = new Event();
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));

        Event result = eventService.findEventById(1L);

        assertThat(result).isEqualTo(event);
        verify(eventRepository).findById(1L);
    }

    @Test
    public void testFindEventById_NotFound() {
        when(eventRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> eventService.findEventById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Event not found with id: 1");
    }
    @Test
    public void testGetEvent() {
        Event event = new Event();
        EventDTO eventDTO = new EventDTO();
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));
        when(eventMapper.toDTO(any(Event.class))).thenReturn(eventDTO);

        EventDTO result = eventService.getEvent(1L);

        assertThat(result).isEqualTo(eventDTO);
        verify(eventRepository).findById(1L);
        verify(eventMapper).toDTO(event);
    }

    @Test
    public void testGetEvent_NotFound() {
        when(eventRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> eventService.getEvent(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Event not found with id: 1");
    }

    @Test
    public void testGetAllEvents() {
        Event event1 = new Event();
        Event event2 = new Event();
        EventDTO eventDTO1 = new EventDTO();
        EventDTO eventDTO2 = new EventDTO();
        when(eventRepository.findAll()).thenReturn(Arrays.asList(event1, event2));
        when(eventMapper.toDTOList(anyList())).thenReturn(Arrays.asList(eventDTO1, eventDTO2));

        List<EventDTO> result = eventService.getAllEvents();

        assertThat(result).containsExactly(eventDTO1, eventDTO2);
        verify(eventRepository).findAll();
        verify(eventMapper).toDTOList(Arrays.asList(event1, event2));
    }

    @Test
    public void testGetAllEventsEmpty() {
        when(eventRepository.findAll()).thenReturn(new ArrayList<>());
        when(eventMapper.toDTOList(anyList())).thenReturn(new ArrayList<>());

        List<EventDTO> result = eventService.getAllEvents();

        assertThat(result).isEmpty();
        verify(eventRepository).findAll();
        verify(eventMapper).toDTOList(new ArrayList<>());
    }

    @Test
    public void testAddEvent() {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(1L);
        eventDTO.setSerie(new SerieDTO());
        eventDTO.getSerie().setId(1L);

        Event event = new Event();
        Serie serie = new Serie();

        when(eventRepository.existsById(anyLong())).thenReturn(false);
        when(eventMapper.toDomain(any(EventDTO.class))).thenReturn(event);
        when(serieService.findSerieById(anyLong())).thenReturn(serie);
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        when(eventMapper.toDTO(any(Event.class))).thenReturn(eventDTO);

        EventDTO result = eventService.addEvent(eventDTO);

        assertThat(result).isEqualTo(eventDTO);
        verify(eventRepository).existsById(eventDTO.getId());
        verify(eventMapper).toDomain(eventDTO);
        verify(serieService).findSerieById(eventDTO.getSerie().getId());
        verify(eventRepository).save(event);
        verify(eventMapper).toDTO(event);
    }

    @Test
    public void testAddEventAlreadyExists() {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(1L);

        when(eventRepository.existsById(anyLong())).thenReturn(true);

        assertThatThrownBy(() -> eventService.addEvent(eventDTO))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Event with id " + eventDTO.getId() + " already exists");
    }

    @Test
    public void testAddEventWithIdZero() {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(0L);
        eventDTO.setSerie(new SerieDTO());
        eventDTO.getSerie().setId(1L);

        Event event = new Event();
        Serie serie = new Serie();

        when(eventMapper.toDomain(any(EventDTO.class))).thenReturn(event);
        when(serieService.findSerieById(anyLong())).thenReturn(serie);
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        when(eventMapper.toDTO(any(Event.class))).thenReturn(eventDTO);

        EventDTO result = eventService.addEvent(eventDTO);

        assertThat(result).isEqualTo(eventDTO);
        verify(eventMapper).toDomain(eventDTO);
        verify(serieService).findSerieById(eventDTO.getSerie().getId());
        verify(eventRepository).save(event);
        verify(eventMapper).toDTO(event);
    }

    @Test
    public void testDeleteEvent() {
        Event event = new Event();
        event.setTags(new ArrayList<>());
        Tag tag = new Tag();
        tag.setEvents(new ArrayList<>(Collections.singletonList(event)));
        event.getTags().add(tag);

        when(eventRepository.existsById(anyLong())).thenReturn(true);
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));

        eventService.deleteEvent(1L);

        assertThat(tag.getEvents()).isEmpty();
        verify(eventRepository).existsById(1L);
        verify(eventRepository).findById(1L);
        verify(eventRepository).save(event);
        verify(eventRepository).deleteById(1L);
    }

    @Test
    public void testDeleteEventNotFound() {
        when(eventRepository.existsById(anyLong())).thenReturn(false);

        assertThatThrownBy(() -> eventService.deleteEvent(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Event not found with id: 1");
    }

    @Test
    public void testUpdateEvent() {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(1L);
        eventDTO.setDate(new Date());
        eventDTO.setValueEvent(2);
        eventDTO.setComment("New Comment");

        Event event = new Event();
        event.setId(1L);
        event.setDate(eventDTO.getDate());
        event.setValueEvent(eventDTO.getValueEvent());
        event.setComment(eventDTO.getComment());

        when(eventRepository.existsById(eventDTO.getId())).thenReturn(true);
        when(eventMapper.toDomain(eventDTO)).thenReturn(event);
        when(eventRepository.save(event)).thenReturn(event);
        when(eventMapper.toDTO(event)).thenReturn(eventDTO);

        EventDTO result = eventService.updateEvent(eventDTO);

        assertThat(result).isEqualTo(eventDTO);
        verify(eventRepository).existsById(eventDTO.getId());
        verify(eventMapper).toDomain(eventDTO);
        verify(eventRepository).save(event);
        verify(eventMapper).toDTO(event);
    }

    @Test
    public void testUpdateEvent_NotExists() {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(1L);
        eventDTO.setDate(new Date());
        eventDTO.setValueEvent(2);
        eventDTO.setComment("New Comment");

        when(eventRepository.existsById(eventDTO.getId())).thenReturn(false);

        assertThatThrownBy(() -> eventService.updateEvent(eventDTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Event not found with id: " + eventDTO.getId());

        verify(eventRepository).existsById(eventDTO.getId());
    }

    @Test
    public void testAssociateTagToEvent() {
        Event event = new Event();
        event.setTags(new ArrayList<>());
        Tag tag = new Tag();
        tag.setEvents(new ArrayList<>());

        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));
        when(tagService.findTagById(anyLong())).thenReturn(tag);

        eventService.associateTagToEvent(1L, 1L);

        assertThat(event.getTags()).contains(tag);
        assertThat(tag.getEvents()).contains(event);
    }

    @Test
    public void testAssociateTagToEvent_EventNotFound() {
        Tag tag = new Tag();
        tag.setEvents(new ArrayList<>());

        when(eventRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> eventService.associateTagToEvent(1L, 1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Event not found with id: 1");
    }

    @Test
    public void testAssociateTagToEvent_TagNotFound() {
        Event event = new Event();
        event.setTags(new ArrayList<>());

        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));
        when(tagService.findTagById(anyLong())).thenThrow(new EntityNotFoundException("Tag not found with id: 1"));

        assertThatThrownBy(() -> eventService.unlinkTagFromEvent(1L, 1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Tag not found with id: 1");
    }

    @Test
    public void testUnlinkTagFromEvent() {
        Event event = new Event();
        Tag tag = new Tag();
        event.setTags(new ArrayList<>(List.of(tag)));
        tag.setEvents(new ArrayList<>(List.of(event)));

        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));
        when(tagService.findTagById(anyLong())).thenReturn(tag);

        eventService.unlinkTagFromEvent(1L, 1L);

        assertThat(event.getTags()).doesNotContain(tag);
        assertThat(tag.getEvents()).doesNotContain(event);
    }








}
