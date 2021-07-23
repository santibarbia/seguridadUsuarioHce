package com.sanatoriodelsalvador.seguridad.repository;

import com.sanatoriodelsalvador.seguridad.entity.Rol;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Integer>{
    
}