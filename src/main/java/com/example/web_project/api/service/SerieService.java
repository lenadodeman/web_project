package com.example.web_project.api.service;

import com.example.web_project.api.model.Serie;
import com.example.web_project.api.repository.SerieRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SerieService {

    private final SerieRepository serieRepository;

    public SerieService(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    public Serie getTimeSerie(final long id_serie)
    {
        return serieRepository.findById(id_serie).orElseThrow(() -> new EntityNotFoundException("Serie not found with id: " + id_serie));
    }

    public Iterable<Serie> getAllTimeSeries()
    {
        return serieRepository.findAll();
    }

    public Serie addSerie(Serie serie)
    {
        if(serieRepository.existsById(serie.getId()) && serie.getId() != 0)
        {
            throw new IllegalStateException("Serie with id " + serie.getId() + " already exists");
        }
        return serieRepository.save(serie);
    }

    public void deleteSerie(final long id_serie)
    {
        if(!serieRepository.existsById(id_serie))
        {
            throw new IllegalStateException("Serie with id " + id_serie + " already exists");
        }
        serieRepository.deleteById(id_serie);
    }

    @Transactional
    public Serie updateSerie(Serie updateSerie)
    {
        if(!serieRepository.existsById(updateSerie.getId()))
        {
            throw new EntityNotFoundException("Serie not found with id: " + updateSerie.getId());
        }
        updateSerie.setTitle(updateSerie.getTitle());
        updateSerie.setDescription(updateSerie.getDescription());
        return serieRepository.save(updateSerie);
    }
}
