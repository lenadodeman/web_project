package com.example.web_project.api.repository;

import com.example.web_project.api.model.SerieAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SerieAcessRepository extends JpaRepository<SerieAccess, Long> {

    Optional<SerieAccess> findBySerieIdAndUserId(Long serieId, Long userId);

//    List<SerieAccess> findByUserId(Long userId);
}
