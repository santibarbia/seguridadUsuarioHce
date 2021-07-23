package com.sanatoriodelsalvador.seguridad.controller;

import java.util.ArrayList;
import java.util.List;

import com.sanatoriodelsalvador.seguridad.entity.Rol;
import com.sanatoriodelsalvador.seguridad.model.response.RolResponse;
import com.sanatoriodelsalvador.seguridad.repository.RolRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("roles")
public class RolController{
    
    @Autowired
    private RolRepository rolRepository;

    @GetMapping("/")
    public List<RolResponse> Get(){
        List<Rol> roles = rolRepository.findAll();

        List<RolResponse> response = new ArrayList<>();

        for (Rol rol : roles) {
            RolResponse rolResponse = new RolResponse();

            rolResponse.setId(rol.getId());
            rolResponse.setNombre(rol.getNombre());

            response.add(rolResponse);
        }

        return response;
    }
}