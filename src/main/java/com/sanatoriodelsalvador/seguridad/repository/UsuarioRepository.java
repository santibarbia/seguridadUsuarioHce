package com.sanatoriodelsalvador.seguridad.repository;

import java.util.List;

import com.sanatoriodelsalvador.seguridad.entity.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    public abstract Usuario findByUsuario(String usuario);
    public abstract List<Usuario> findAll();
}