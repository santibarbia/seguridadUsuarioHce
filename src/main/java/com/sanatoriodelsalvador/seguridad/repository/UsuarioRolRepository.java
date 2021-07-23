package com.sanatoriodelsalvador.seguridad.repository;

import java.util.List;

import com.sanatoriodelsalvador.seguridad.entity.Rol;
import com.sanatoriodelsalvador.seguridad.entity.Usuario;
import com.sanatoriodelsalvador.seguridad.entity.UsuarioRol;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, Integer>{
    public abstract UsuarioRol findByRolInAndUsuario(Rol rol, Usuario usuario);
    public abstract List<UsuarioRol> findByUsuario(Usuario usuario);
}