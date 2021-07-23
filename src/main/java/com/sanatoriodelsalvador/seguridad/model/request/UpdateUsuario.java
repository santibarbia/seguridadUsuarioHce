package com.sanatoriodelsalvador.seguridad.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description =  "Entidad Usuario a modificar en base de datos")
public class UpdateUsuario{ 
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

    @ApiModelProperty(notes = "Usuario Activo")
    @JsonProperty
    private Boolean activo;
}