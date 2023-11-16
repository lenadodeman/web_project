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
        return serieRepository.save(serie);
    }

    public void deleteSerie(final long id_serie)
    {
        serieRepository.deleteById(id_serie);
    }

    @Transactional
    public Serie updateSerie(Serie updateSerie)
    {
        updateSerie.setTitle(updateSerie.getTitle());
        updateSerie.setDescription(updateSerie.getDescription());
        return serieRepository.save(updateSerie);
    }
}
