package com.example.web_project.service;

import com.example.web_project.api.dto.EventDTO;
import com.example.web_project.api.model.Event;
import com.example.web_project.api.model.Tag;
import com.example.web_project.api.repository.EventRepository;
import com.example.web_project.api.service.EventService;
import com.example.web_project.api.service.TagService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

    @InjectMocks
    private EventService eventService;

    @Test
    public void testGetEvent() {
        Event event = new Event();
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));

        Event result = eventService.getEvent(1L);

        assertThat(result).isEqualTo(event);
        verify(eventRepository).findById(1L);
    }

    @Test
    public void testGetEventNotFound() {
        when(eventRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> eventService.getEvent(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Event not found with id: 1");
    }

    @Test
    public void testGetAllEvents() {
        Event event1 = new Event();
        Event event2 = new Event();
        when(eventRepository.findAll()).thenReturn(Arrays.asList(event1, event2));

        Iterable<Event> result = eventService.getAllEvents();

        assertThat(result).containsExactly(event1, event2);
    }

    @Test
    public void testGetAllEventsEmpty() {
        when(eventRepository.findAll()).thenReturn(new ArrayList<>());

        Iterable<Event> result = eventService.getAllEvents();

        assertThat(result).isEmpty();
        verify(eventRepository).findAll();
    }

    @Test
    public void testAddEvent() {
        Event event = new Event();
        event.setId(1L);
        when(eventRepository.existsById(anyLong())).thenReturn(false);
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event result = eventService.addEvent(event);

        assertThat(result).isEqualTo(event);
        verify(eventRepository).existsById(event.getId());
        verify(eventRepository).save(event);
    }

    @Test
    public void testAddEventAlreadyExists() {
        Event event = new Event();
        event.setId(1L);
        when(eventRepository.existsById(anyLong())).thenReturn(true);

        assertThatThrownBy(() -> eventService.addEvent(event))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Event with id " + event.getId() + " already exists");
    }

    @Test
    public void testAddEventWithIdZero() {
        Event event = new Event();
        event.setId(0L);
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event result = eventService.addEvent(event);

        assertThat(result).isEqualTo(event);
        verify(eventRepository).existsById(event.getId());
        verify(eventRepository).save(event);
    }

    @Test
    public void testDeleteEvent() {
        doNothing().when(eventRepository).deleteById(anyLong());
        when(eventRepository.existsById(anyLong())).thenReturn(true);

        eventService.deleteEvent(1L);

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
    public void testAssociateTagToEvent() {
        Event event = new Event();
        event.setTags(new ArrayList<>());
        Tag tag = new Tag();
        tag.setEvents(new ArrayList<>());

        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));
        when(tagService.getTag(anyLong())).thenReturn(tag);

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
        when(tagService.getTag(anyLong())).thenThrow(new EntityNotFoundException("Tag not found with id: 1"));

        assertThatThrownBy(() -> eventService.associateTagToEvent(1L, 1L))
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
        when(tagService.getTag(anyLong())).thenReturn(tag);

        eventService.unlinkTagFromEvent(1L, 1L);

        assertThat(event.getTags()).doesNotContain(tag);
        assertThat(tag.getEvents()).doesNotContain(event);
    }

    @Test
    public void testUnlinkTagFromEvent_EventNotFound() {
        Tag tag = new Tag();
        tag.setEvents(new ArrayList<>());

        when(eventRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> eventService.unlinkTagFromEvent(1L, 1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Event not found with id: 1");
    }

    @Test
    public void testUnlinkTagFromEvent_TagNotFound() {
        Event event = new Event();
        event.setTags(new ArrayList<>());

        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));
        when(tagService.getTag(anyLong())).thenThrow(new EntityNotFoundException("Tag not found with id: 1"));

        assertThatThrownBy(() -> eventService.unlinkTagFromEvent(1L, 1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Tag not found with id: 1");
    }

    @Test
    public void testUpdateEventDetails() {
        Event existingEvent = new Event();
        existingEvent.setId(1L);
        existingEvent.setDate(new Date());
        existingEvent.setValueEvent(1);
        existingEvent.setComment("Old Comment");

        Event updateEvent = new Event();
        updateEvent.setId(1L);
        updateEvent.setDate(new Date());
        updateEvent.setValueEvent(2);
        updateEvent.setComment("New Comment");

        when(eventRepository.existsById(anyLong())).thenReturn(true);
        when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Event result = eventService.updateEvent(updateEvent);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getDate()).isEqualTo(updateEvent.getDate());
        assertThat(result.getValueEvent()).isEqualTo(2);
        assertThat(result.getComment()).isEqualTo("New Comment");
        verify(eventRepository).save(updateEvent);
    }

    @Test
    public void testUpdateEventNotFound() {
        Event updateEvent = new Event();
        updateEvent.setId(1L);
        updateEvent.setDate(new Date());
        updateEvent.setValueEvent(1);
        updateEvent.setComment("New Comment");

        when(eventRepository.existsById(anyLong())).thenReturn(false);

        assertThatThrownBy(() -> eventService.updateEvent(updateEvent))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Event not found with id: " + updateEvent.getId());
    }



}
