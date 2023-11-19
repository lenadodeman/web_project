package com.example.web_project.service;

import com.example.web_project.api.model.Serie;
import com.example.web_project.api.repository.SerieRepository;
import com.example.web_project.api.service.SerieService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SerieServiceTest {
    @Mock
    private SerieRepository serieRepository;
    @InjectMocks
    private SerieService serieService;

    @Test
    public void testGetTimeSerie() {
        Serie serie = new Serie();
        when(serieRepository.findById(anyLong())).thenReturn(Optional.of(serie));

        Serie result = serieService.getTimeSerie(1L);

        assertThat(result).isEqualTo(serie);
        verify(serieRepository).findById(1L);
    }

    @Test
    public void testGetTimeSerieNotFound() {
        when(serieRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> serieService.getTimeSerie(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Serie not found with id: 1");
    }

    @Test
    public void testGetAllTimeSeries() {
        Serie serie1 = new Serie();
        Serie serie2 = new Serie();
        when(serieRepository.findAll()).thenReturn(Arrays.asList(serie1, serie2));

        Iterable<Serie> result = serieService.getAllTimeSeries();

        assertThat(result).containsExactly(serie1, serie2);
        verify(serieRepository).findAll();
    }

    @Test
    public void testGetAllTimeSeriesEmpty() {
        when(serieRepository.findAll()).thenReturn(new ArrayList<>());

        Iterable<Serie> result = serieService.getAllTimeSeries();

        assertThat(result).isEmpty();
        verify(serieRepository).findAll();
    }

    @Test
    public void testAddSerie() {
        Serie newSerie  = new Serie();
        newSerie.setTitle("Science Series");
        newSerie.setDescription("A series about interesting scientific numbers");
        when(serieRepository.existsById(anyLong())).thenReturn(false);
        when(serieRepository.save(any(Serie.class))).thenReturn(newSerie);

        Serie result = serieService.addSerie(newSerie);

        assertThat(result.getTitle()).isEqualTo(newSerie.getTitle());
        assertThat(result.getDescription()).isEqualTo(newSerie.getDescription());
    }

    @Test
    public void testAddSerieAlreadyExists() {
        Serie serie = new Serie();
        serie.setId(1L);
        when(serieRepository.existsById(anyLong())).thenReturn(true);

        assertThatThrownBy(() -> serieService.addSerie(serie))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Serie with id 1 already exists");
    }

    @Test
    public void testAddSerieWithDatabaseError() {
        Serie newSerie = new Serie();
        when(serieRepository.existsById(anyLong())).thenReturn(false);
        when(serieRepository.save(any(Serie.class))).thenThrow(new RuntimeException("Database Error"));

        assertThatThrownBy(() -> serieService.addSerie(newSerie))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Database Error");
    }

    @Test
    public void testDeleteSerie() {
        doNothing().when(serieRepository).deleteById(anyLong());
        when(serieRepository.existsById(anyLong())).thenReturn(true);

        serieService.deleteSerie(1L);

        verify(serieRepository).deleteById(1L);
    }

    @Test
    public void testDeleteSerieNotFound() {
        when(serieRepository.existsById(anyLong())).thenReturn(false);

        assertThatThrownBy(() -> serieService.deleteSerie(1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Serie with id 1 already exists");
    }

    @Test
    public void testUpdateSerieDetails() {
        Serie existingSerie = new Serie();
        existingSerie.setId(1L);
        existingSerie.setTitle("Old Title");
        existingSerie.setDescription("Old Description");

        Serie updateSerie = new Serie();
        updateSerie.setId(1L);
        updateSerie.setTitle("New Title");
        updateSerie.setDescription("New Description");

        when(serieRepository.existsById(anyLong())).thenReturn(true);
        when(serieRepository.save(any(Serie.class))).thenReturn(updateSerie);

        Serie result = serieService.updateSerie(updateSerie);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("New Title");
        assertThat(result.getDescription()).isEqualTo("New Description");
        verify(serieRepository).save(updateSerie);
    }

    @Test
    public void testUpdateSerieNotFound() {
        Serie updateSerie = new Serie();
        updateSerie.setId(1L);
        updateSerie.setTitle("New Title");
        updateSerie.setDescription("New Description");

        when(serieRepository.existsById(anyLong())).thenReturn(false);

        assertThatThrownBy(() -> serieService.updateSerie(updateSerie))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Serie not found with id: " + updateSerie.getId());
    }
}
