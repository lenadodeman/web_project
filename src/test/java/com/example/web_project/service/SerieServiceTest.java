//package com.example.web_project.service;
//
//import com.example.web_project.api.dto.SerieDTO;
//import com.example.web_project.api.mapper.SerieMapper;
//import com.example.web_project.api.model.Serie;
//import com.example.web_project.api.repository.SerieRepository;
//import com.example.web_project.api.service.SerieService;
//import jakarta.persistence.EntityNotFoundException;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.dao.DataAccessException;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.mockito.Mockito.*;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//@ExtendWith(MockitoExtension.class)
//public class SerieServiceTest {
//    @Mock
//    private SerieRepository serieRepository;
//
//    @Mock
//    private SerieMapper serieMapper;
//
//    @InjectMocks
//    private SerieService serieService;
//
//
//    @Test
//    public void testFindSerieById() {
//        Serie serie = new Serie();
//        when(serieRepository.findById(anyLong())).thenReturn(Optional.of(serie));
//
//        Serie result = serieService.findSerieById(1L);
//
//        assertThat(result).isEqualTo(serie);
//        verify(serieRepository).findById(1L);
//    }
//
//    @Test
//    public void testFindSerieById_NotFound() {
//        when(serieRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> serieService.findSerieById(1L))
//                .isInstanceOf(EntityNotFoundException.class)
//                .hasMessageContaining("Serie not found with id: 1");
//    }
//
//    @Test
//    public void testGetTimeSerie() {
//        Serie serie = new Serie();
//        SerieDTO serieDTO = new SerieDTO();
//        when(serieRepository.findById(anyLong())).thenReturn(Optional.of(serie));
//        when(serieMapper.toDTO(any(Serie.class))).thenReturn(serieDTO);
//
//        SerieDTO result = serieService.getTimeSerie(1L);
//
//        assertEquals(result, serieDTO);
//        verify(serieRepository).findById(1L);
//        verify(serieMapper).toDTO(serie);
//    }
//
//    @Test
//    public void testGetAllTimeSeries() {
//        Serie serie1 = new Serie();
//        Serie serie2 = new Serie();
//        SerieDTO serieDTO1 = new SerieDTO();
//        SerieDTO serieDTO2 = new SerieDTO();
//        when(serieRepository.findAll()).thenReturn(Arrays.asList(serie1, serie2));
//        when(serieMapper.toDTOList(anyList())).thenReturn(Arrays.asList(serieDTO1, serieDTO2));
//
//        List<SerieDTO> result = serieService.getAllTimeSeries();
//
//        assertThat(result).containsExactly(serieDTO1, serieDTO2);
//        verify(serieRepository).findAll();
//        verify(serieMapper).toDTOList(Arrays.asList(serie1, serie2));
//    }
//
//
//
//    @Test
//    public void testAddSerie() {
//        SerieDTO serieDTO = new SerieDTO();
//        serieDTO.setId(1L);
//
//        when(serieRepository.existsById(anyLong())).thenReturn(false);
//        when(serieMapper.toDomain(any(SerieDTO.class))).thenReturn(new Serie());
//        when(serieRepository.save(any(Serie.class))).thenReturn(new Serie());
//        when(serieMapper.toDTO(any(Serie.class))).thenReturn(serieDTO);
//
//        SerieDTO result = serieService.addSerie(serieDTO);
//
//        assertThat(result).isEqualTo(serieDTO);
//        verify(serieRepository).existsById(1L);
//        verify(serieMapper).toDomain(serieDTO);
//        verify(serieRepository).save(any(Serie.class));
//        verify(serieMapper).toDTO(any(Serie.class));
//    }
//
//    @Test
//    public void testAddSerieAlreadyExists() {
//        SerieDTO serieDTO = new SerieDTO();
//        serieDTO.setId(1L);
//
//        when(serieRepository.existsById(anyLong())).thenReturn(true);
//
//        assertThatThrownBy(() -> serieService.addSerie(serieDTO))
//                .isInstanceOf(IllegalStateException.class)
//                .hasMessageContaining("Serie with id 1 already exists");
//    }
//
//    @Test
//    public void testDeleteSerie() {
//        when(serieRepository.existsById(anyLong())).thenReturn(true);
//
//        serieService.deleteSerie(1L);
//
//        verify(serieRepository).existsById(1L);
//        verify(serieRepository).deleteById(1L);
//    }
//
//    @Test
//    public void testDeleteSerieNotFound() {
//        when(serieRepository.existsById(anyLong())).thenReturn(false);
//
//        assertThatThrownBy(() -> serieService.deleteSerie(1L))
//                .isInstanceOf(IllegalStateException.class)
//                .hasMessageContaining("Serie with id 1 already exists");
//    }
//
//    @Test
//    public void testUpdateSerie() {
//        SerieDTO serieDTO = new SerieDTO();
//        serieDTO.setId(1L);
//
//        when(serieRepository.existsById(anyLong())).thenReturn(true);
//        when(serieMapper.toDomain(any(SerieDTO.class))).thenReturn(new Serie());
//        when(serieRepository.save(any(Serie.class))).thenReturn(new Serie());
//        when(serieMapper.toDTO(any(Serie.class))).thenReturn(serieDTO);
//
//        SerieDTO result = serieService.updateSerie(serieDTO);
//
//        assertThat(result).isEqualTo(serieDTO);
//        verify(serieRepository).existsById(1L);
//        verify(serieMapper).toDomain(serieDTO);
//        verify(serieRepository).save(any(Serie.class));
//        verify(serieMapper).toDTO(any(Serie.class));
//    }
//
//    @Test
//    public void testUpdateSerie_NotExists() {
//        SerieDTO serieDTO = new SerieDTO();
//        serieDTO.setId(1L);
//
//        when(serieRepository.existsById(anyLong())).thenReturn(false);
//
//        assertThatThrownBy(() -> serieService.updateSerie(serieDTO))
//                .isInstanceOf(EntityNotFoundException.class)
//                .hasMessageContaining("Serie not found with id: 1");
//    }
//}
