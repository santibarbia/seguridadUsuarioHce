package com.sanatoriodelsalvador.seguridad.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "seg_roles", schema = "seguridad")
public class Rol{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol", unique = true, nullable = false)
    private Integer id;

    @Column(name = "rol", unique = true, nullable = false, length = 50)
    private String nombre;

    @Column(name = "activo", nullable = false)
    private Boolean activo;
}