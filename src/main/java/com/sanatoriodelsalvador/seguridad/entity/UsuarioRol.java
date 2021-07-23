package com.sanatoriodelsalvador.seguridad.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "seg_usuarios_roles", 
    schema = "seguridad", 
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_usuario", "id_rol"}))
public class UsuarioRol{
    @Id
    @GenericGenerator(name = "id_usuario_rol_seq", strategy = "com.sanatoriodelsalvador.seguridad.commons.hibernate.UsuarioRolIdGenerator")
    @GeneratedValue(generator = "id_usuario_rol_seq")
    @Column(name = "id_usuario_rol")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    @Column(name = "defecto", nullable = false, columnDefinition = "BIT DEFAULT 0")
    private Boolean defecto;
}