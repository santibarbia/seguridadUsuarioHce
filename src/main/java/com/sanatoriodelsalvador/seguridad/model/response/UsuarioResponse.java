package com.sanatoriodelsalvador.seguridad.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description =  "Respuesta")
public class UsuarioResponse{

    @ApiModelProperty(notes = "id de Usuario")
    @JsonProperty
    private Integer id;

    @ApiModelProperty(notes = "id de Jefe")
    @JsonProperty
    private Integer idJefe;

    @ApiModelProperty(notes = "id de Sector")
    @JsonProperty
    private Integer idSector;

    @ApiModelProperty(notes = "Apellidos de usuario")
    @JsonProperty
    private String apellido;

    @ApiModelProperty(notes = "Nombres de usuario")
    @JsonProperty
    private String nombre;

    @ApiModelProperty(notes = "Email de usuario")
    @JsonProperty
    private String email;

    @ApiModelProperty(notes = "Matricula de usuario")
    @JsonProperty
    private String matricula;

    @ApiModelProperty(notes = "Usuario activo")
    @JsonProperty
    private boolean activo;
}