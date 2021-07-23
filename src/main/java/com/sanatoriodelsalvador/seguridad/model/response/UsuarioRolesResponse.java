package com.sanatoriodelsalvador.seguridad.model.response;

import java.util.List;

import lombok.Data;

@Data
public class UsuarioRolesResponse{
    public Integer id;
    public List<RolResponse> roles;
}