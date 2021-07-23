package com.sanatoriodelsalvador.seguridad.entity;

import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "seg_usuarios", schema = "seguridad")
public class Usuario{

    @Id
    @GenericGenerator(name = "id_usuario", strategy = "com.sanatoriodelsalvador.seguridad.commons.hibernate.UsuarioIdGenerator")
    @GeneratedValue(generator = "id_usuario")
    @Column(name = "id_usuario", unique = true, nullable = false)
    private Integer id;

    @Column(name = "id_usuario_jefe", nullable = true)
    private Integer idJefe;

    @Column(name = "id_sector", nullable = false)
    private Integer idSector;

    @Column(name = "usuario", nullable = false, length = 20, unique = true)
    private String usuario;

    @Column(name = "apellido", nullable = true, length = 75)
    private String apellido;

    @Column(name = "nombre", nullable = true, length = 75)
    private String nombre;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "hora_desde", nullable = true)
    private LocalTime horaDesde;

    @Column(name = "hora_hasta", nullable = true)
    private LocalTime horaHasta;

    @Column(name = "ultimo_reseteo_pass")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date ultimoReset;

    @Column(name = "matricula")
    private String matricula;

    public Usuario(Integer idJefe, 
                    Integer idSector, 
                    String usuario, 
                    String apellido, 
                    String nombre,
                    String password, 
                    String email, 
                    String matricula){
        
        this.idJefe = idJefe;
        this.idSector = idSector;
        this.usuario = usuario;
        this.apellido = apellido;
        this.password = password;
        this.email = email;
        this.matricula = matricula;
        this.ultimoReset = new Date();
        this.nombre = nombre;        
        this.activo = true;
    }

}