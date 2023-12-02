package com.example.web_project.api.service;

import com.example.web_project.api.dto.SerieDTO;
import com.example.web_project.api.mapper.SerieMapper;
import com.example.web_project.api.model.Serie;
import com.example.web_project.api.repository.SerieRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieService {

    private final SerieRepository serieRepository;

    private final SerieMapper serieMapper = SerieMapper.INSTANCE;

    public SerieService(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    public Serie findSerieById(final long serieId)
    {
        return serieRepository.findById(serieId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Serie not found with id: " + serieId)
                );
    }

    public SerieDTO getTimeSerie(long serieId){
        return serieMapper.toDTO(findSerieById(serieId));
    }

    public List<SerieDTO> getAllTimeSeries()
    {
        return serieMapper.toDTOList(serieRepository.findAll());
    }

    public SerieDTO addSerie(SerieDTO serieDTO)
    {
        if(serieRepository.existsById(serieDTO.getId()))
        {
            throw new IllegalStateException("Serie with id " + serieDTO.getId() + " already exists");
        }

        Serie serieToAdd = serieMapper.toDomain(serieDTO);

        return serieMapper.toDTO(serieRepository.save(serieToAdd));
    }

    public void deleteSerie(final long serieId)
    {
        if(!serieRepository.existsById(serieId))
        {
            throw new IllegalStateException("Serie with id " + serieId + " already exists");
        }
        serieRepository.deleteById(serieId);
    }

    @Transactional
    public SerieDTO updateSerie(SerieDTO SerieDTO)
    {
        if(!serieRepository.existsById(SerieDTO.getId()))
        {
            throw new EntityNotFoundException("Serie not found with id: " + SerieDTO.getId());
        }

        Serie serieToUpdate = serieMapper.toDomain(SerieDTO);

        return serieMapper.toDTO(serieRepository.save(serieToUpdate));
    }
}
