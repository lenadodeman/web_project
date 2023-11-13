package com.example.web_project.api.service;

import com.example.web_project.api.model.Serie;
import com.example.web_project.api.repository.SerieRepository;
import org.springframework.stereotype.Service;

@Service
public class SerieService {

    private final SerieRepository serieRepository;

    public SerieService(SerieRepository timeSerieRepository) {
        this.serieRepository = timeSerieRepository;
    }

    public Serie getTimeSerie(final long idTimeSerie)
    {
        return serieRepository.findById(idTimeSerie).orElseThrow(IllegalArgumentException::new);
    }

    public Iterable<Serie> getAllTimeSeries()
    {
        return serieRepository.findAll();
    }

    public Serie addSerie(Serie serie)
    {
        return serieRepository.save(serie);
    }

    public void deleteSerie(long id_serie)
    {
        serieRepository.deleteById(id_serie);
    }
}
