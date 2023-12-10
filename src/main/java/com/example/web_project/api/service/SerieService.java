package com.example.web_project.api.service;

import com.example.web_project.api.dto.EventDTO;
import com.example.web_project.api.dto.SerieAcessDTO;
import com.example.web_project.api.dto.SerieDTO;
import com.example.web_project.api.mapper.EventMapper;
import com.example.web_project.api.mapper.SerieAcessMapper;
import com.example.web_project.api.mapper.SerieMapper;
import com.example.web_project.api.model.Serie;
import com.example.web_project.api.model.SerieAccess;
import com.example.web_project.api.model.User;
import com.example.web_project.api.repository.SerieAcessRepository;
import com.example.web_project.api.repository.SerieRepository;
import com.example.web_project.api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SerieService {

    private final SerieRepository serieRepository;

    private final SerieMapper serieMapper = SerieMapper.INSTANCE;

    private final SerieAcessMapper serieAcessMapper = SerieAcessMapper.INSTANCE;
    private final SerieAcessRepository serieAccessRepository;

    private final EventMapper eventMapper;
    private final UserService userService;

    private final UserRepository userRepository;
    public SerieService(SerieRepository serieRepository, SerieAcessRepository serieAcessRepository, EventMapper eventMapper, UserService userService, UserRepository userRepository) {
        this.serieRepository = serieRepository;
        this.serieAccessRepository = serieAcessRepository;
        this.eventMapper = eventMapper;
        this.userService = userService;
        this.userRepository = userRepository;
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
        List<Serie> series = serieRepository.findAll();
        return series.stream().map(this::mapSerieWithEvents).collect(Collectors.toList());
    }

    private SerieDTO mapSerieWithEvents(Serie serie) {
        SerieDTO serieDTO = serieMapper.toDTO(serie);


        List<EventDTO> eventDTOs = eventMapper.toDTOList(serie.getEventList());
        serieDTO.setEventList(eventDTOs);

        return serieDTO;
    }

    public List<SerieDTO> getSeriesByCurrentUserId()
    {
        Long userId = userService.getCurrentUserId();
        List<SerieAccess> serieAccesses = serieAccessRepository.findByUserId(userId);
        List<Serie> series = serieAccesses.stream()
                .map(SerieAccess::getSerie)
                .collect(Collectors.toList());
        return series.stream().map(this::mapSerieWithEvents).collect(Collectors.toList());
    }

    public SerieDTO addSerie(SerieDTO serieDTO)
    {
        if(serieRepository.existsById(serieDTO.getId()))
        {
            throw new IllegalStateException("Serie with id " + serieDTO.getId() + " already exists");
        }

        Long userID = userService.getCurrentUserId();

        User user = userService.findUserById(userID);

        Serie serieToAdd = serieMapper.toDomain(serieDTO);

        Serie savedSerie = serieRepository.save(serieToAdd);

        SerieAccess serieAccess = new SerieAccess();
        serieAccess.setSerie(savedSerie);
        serieAccess.setUser(user);
        serieAccess.setAccessType("WRITE");
        serieAccessRepository.save(serieAccess);

        return serieMapper.toDTO(savedSerie);
    }

    public void deleteSerie(final long serieId) throws AccessDeniedException {
        Long userId = userService.getCurrentUserId();
        SerieDTO serieDTO = getTimeSerie(serieId);
        SerieAccess access = serieAccessRepository.findBySerieIdAndUserId(serieDTO.getId(), userId)
                .orElseThrow(() -> new AccessDeniedException("No access to this serie"));

        if (!access.getAccessType().equals("WRITE")) {
            throw new AccessDeniedException("Insufficient permissions");
        }

        if(!serieRepository.existsById(serieId))
        {
            throw new IllegalStateException("Serie with id " + serieId + " already exists");
        }
        serieRepository.deleteById(serieId);
    }

    @Transactional
    public SerieDTO updateSerie(SerieDTO serieDTO) throws AccessDeniedException {

        Long userId = userService.getCurrentUserId();
        SerieAccess access = serieAccessRepository.findBySerieIdAndUserId(serieDTO.getId(), userId)
                .orElseThrow(() -> new AccessDeniedException("No access to this serie"));

        if (!access.getAccessType().equals("WRITE")) {
            throw new AccessDeniedException("Insufficient permissions");
        }

        if(!serieRepository.existsById(serieDTO.getId()))
        {
            throw new EntityNotFoundException("Serie not found with id: " + serieDTO.getId());
        }

        Serie serieToUpdate = serieMapper.toDomain(serieDTO);

        return serieMapper.toDTO(serieRepository.save(serieToUpdate));
    }

    public SerieAcessDTO shareSerieWithUser(SerieAcessDTO serieAcessDTO) {
        long id_user = serieAcessDTO.getUser().getId();
        long id_serie = serieAcessDTO.getSerie().getId();

        Serie serie = findSerieById(serieAcessDTO.getSerie().getId());

        boolean alreadyShared = serieAccessRepository.findBySerieIdAndUserId(id_serie, id_user).isPresent();
        if (alreadyShared) {
            throw new IllegalStateException("Serie already shared with this user");
        }

        User user = userRepository.findById(id_user)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id_user));

        SerieAccess serieAccess = new SerieAccess();
        serieAccess.setSerie(serie);
        serieAccess.setUser(user);
        serieAccess.setAccessType(serieAcessDTO.getAccessType());

        serieAccess = serieAccessRepository.save(serieAccess);

        return serieAcessMapper.toDTO(serieAccess);
    }

    public SerieAcessDTO updateSerieAccess(SerieAcessDTO updateSerieAccessDTO) {
        long id_serie = updateSerieAccessDTO.getSerie().getId();
        long id_user = updateSerieAccessDTO.getUser().getId();
        SerieAccess serieAccess = serieAccessRepository.findBySerieIdAndUserId(id_serie, id_user)
                .orElseThrow(() -> new EntityNotFoundException("SeriesAccess not found for id: "
                        + id_serie + " and id: " + id_user));

        serieAccess.setAccessType(updateSerieAccessDTO.getAccessType());

        return serieAcessMapper.toDTO(serieAccessRepository.save(serieAccess));
    }

    public void removeSerieAccess(SerieAcessDTO serieAcessDTO) {
        long id_serie = serieAcessDTO.getSerie().getId();
        long id_user = serieAcessDTO.getUser().getId();
        SerieAccess seriesAccess = serieAccessRepository.findBySerieIdAndUserId(id_serie, id_user)
                .orElseThrow(() -> new EntityNotFoundException("SeriesAccess not found for id: "
                        + id_serie + " and id: " + id_user));

        serieAccessRepository.delete(seriesAccess);
    }
}
