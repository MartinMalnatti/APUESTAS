package com.apuestasamistosas.app.repositories;

import com.apuestasamistosas.app.entities.direcciones.ProvinciasDatos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DireccionesRepositorio extends JpaRepository<ProvinciasDatos, Long>{


}
