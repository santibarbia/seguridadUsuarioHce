package com.sanatoriodelsalvador.seguridad.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description =  "Entidad para poder cambiar password")
public class ChangePassword{

    @JsonIgnore  
    public int id;

    @ApiModelProperty(notes = "Password actual de usuario")
    @JsonProperty
    public String actualPassword;

    @ApiModelProperty(notes = "Password nueva del usuario")
    @JsonProperty
    public String newPassword;
}