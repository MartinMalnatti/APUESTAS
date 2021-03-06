package com.apuestasamistosas.app.repositories;

import com.apuestasamistosas.app.entities.Eventos;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventosRepositorio extends JpaRepository<Eventos, String> {
    
    @Query("SELECT e FROM Eventos e ORDER BY e.fechaEvento ASC")
    public Optional<List<Eventos>> listAllByDates();
    
    @Query("SELECT e FROM Eventos e WHERE e.expirado = false ORDER BY e.fechaEvento ASC")
    public Optional<List<Eventos>> listAllByDatesAndNoExpired();

}
