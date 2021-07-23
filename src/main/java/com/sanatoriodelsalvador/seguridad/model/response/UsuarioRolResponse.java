package com.sanatoriodelsalvador.seguridad.model.response;

import lombok.Data;

@Data
public class UsuarioRolResponse{
    private Integer idUsuario;
    private Integer idRol;
    private String nombreRol;
}