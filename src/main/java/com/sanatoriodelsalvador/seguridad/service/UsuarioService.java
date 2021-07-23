package com.sanatoriodelsalvador.seguridad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sanatoriodelsalvador.seguridad.entity.*;
import com.sanatoriodelsalvador.seguridad.model.request.ChangePassword;
import com.sanatoriodelsalvador.seguridad.model.request.CreateUsuario;
import com.sanatoriodelsalvador.seguridad.model.request.UpdateUsuario;
import com.sanatoriodelsalvador.seguridad.model.response.RolResponse;
import com.sanatoriodelsalvador.seguridad.model.response.UsuarioResponse;
import com.sanatoriodelsalvador.seguridad.model.response.UsuarioRolResponse;
import com.sanatoriodelsalvador.seguridad.model.response.UsuarioRolesResponse;
import com.sanatoriodelsalvador.seguridad.repository.RolRepository;
import com.sanatoriodelsalvador.seguridad.repository.UsuarioRepository;
import com.sanatoriodelsalvador.seguridad.repository.UsuarioRolRepository;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.mail.MessagingException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRolRepository usuarioRolRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MailService mailsrv;

    public UsuarioResponse saveUsuario(CreateUsuario userCommand) throws Exception {
        Usuario usuarioExistente = usuarioRepository.findByUsuario(userCommand.getUsuario());

        if (usuarioExistente != null) {
            throw new Exception("Usuario Existente");
        }

        Usuario user = new Usuario(userCommand.getIdJefe(), userCommand.getIdSector(), userCommand.getUsuario(),
                userCommand.getApellido(), userCommand.getNombre(),
                bCryptPasswordEncoder.encode(userCommand.getPassword()), userCommand.getEmail(),
                userCommand.getMatricula());

        user = usuarioRepository.save(user);

        ModelMapper modelMapper = new ModelMapper();
        UsuarioResponse dto = modelMapper.map(user, UsuarioResponse.class);

        return dto;
    }

    public UsuarioResponse getUsuario(Integer id) throws Exception {

        Usuario user = findById(id);

        ModelMapper modelMapper = new ModelMapper();
        UsuarioResponse dto = modelMapper.map(user, UsuarioResponse.class);

        return dto;
    }

    public UsuarioResponse updateUsuario(Integer id, UpdateUsuario userCommand) throws Exception {
        Usuario user = findById(id);

        if (userCommand.getApellido() != null) {
            user.setApellido(userCommand.getApellido());
        }

        if (userCommand.getEmail() != null) {
            user.setEmail(userCommand.getEmail());
        }

        if (userCommand.getIdJefe() != null) {
            user.setIdJefe(userCommand.getIdJefe());
        }

        if (userCommand.getIdSector() != null) {
            user.setIdSector(userCommand.getIdSector());
        }

        if (userCommand.getMatricula() != null) {
            user.setMatricula(userCommand.getMatricula());
        }

        if (userCommand.getActivo() != null) {
            user.setActivo(userCommand.getActivo());
        }

        if (userCommand.getNombre() != null) {
            user.setNombre(userCommand.getNombre());
        }

        usuarioRepository.save(user);

        ModelMapper modelMapper = new ModelMapper();
        UsuarioResponse dto = modelMapper.map(user, UsuarioResponse.class);

        return dto;
    }

    private Usuario findById(Integer id) throws Exception {

        Optional<Usuario> user = usuarioRepository.findById(id);

        if (!user.isPresent()) {
            throw new Exception("Usuario no existente");
        }

        return user.get();
    }

    private Rol getRol(Integer id) throws Exception {
        Optional<Rol> rol = rolRepository.findById(id);

        if (!rol.isPresent()) {
            throw new Exception("Usuario no existente");
        }

        return rol.get();
    }

    public UsuarioRolResponse setRol(Integer id, Integer idRol) throws Exception {
        Usuario user = findById(id);
        Rol rol = getRol(idRol);

        if (usuarioRolRepository.findByRolInAndUsuario(rol, user) != null) {
            throw new Exception("Rol anteriormente asignado a el usuario");
        };

        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setDefecto(true);
        usuarioRol.setRol(rol);
        usuarioRol.setUsuario(user);

        usuarioRol = usuarioRolRepository.save(usuarioRol);

        UsuarioRolResponse response = new UsuarioRolResponse();

        response.setIdRol(usuarioRol.getRol().getId());
        response.setIdUsuario(usuarioRol.getUsuario().getId());
        response.setNombreRol(usuarioRol.getRol().getNombre());

        return response;
    }

    public void deleteRol(Integer id, Integer idRol) throws Exception {
        Usuario user = findById(id);
        Rol rol = getRol(idRol);

        UsuarioRol userRol = usuarioRolRepository.findByRolInAndUsuario(rol, user);

        if (userRol == null) {
            throw new Exception("Rol no asignado a el usuario");
        }

        usuarioRolRepository.delete(userRol);
    }

    public void changePassword(ChangePassword changePasswordCommand) throws Exception {
        Usuario user = findById(changePasswordCommand.getId());
        Date ultimoReset = new Date();
        boolean passwordValid = bCryptPasswordEncoder.matches(changePasswordCommand.getActualPassword(), user.getPassword());

        if (!passwordValid) {
            throw new Exception("Password incorrecta");
        }

        user.setPassword(bCryptPasswordEncoder.encode(changePasswordCommand.getNewPassword()));
        user.setUltimoReset(ultimoReset);
        usuarioRepository.save(user);
    }

    public UsuarioRolesResponse getRolesById(Integer idUser) throws Exception {
        Usuario user = findById(idUser);

        List<UsuarioRol> roles = usuarioRolRepository.findByUsuario(user);

        UsuarioRolesResponse response = new UsuarioRolesResponse();
        response.setId(idUser);

        List<RolResponse> rolesResponse = new ArrayList<>();

        for (UsuarioRol usuarioRol : roles) {

            RolResponse rolResponse = new RolResponse();

            rolResponse.setId(usuarioRol.getRol().getId());
            rolResponse.setNombre(usuarioRol.getRol().getNombre());

            rolesResponse.add(rolResponse);
        }

        response.setRoles(rolesResponse);

        return response;
    }

    public UsuarioRolResponse getRol(Integer id, Integer idRol) throws Exception {
        Usuario user = findById(id);
        Rol rol = getRol(idRol);

        UsuarioRol userRol = usuarioRolRepository.findByRolInAndUsuario(rol, user);

        if (userRol == null) {
            throw new Exception("Rol no asignado a el usuario");
        }

        UsuarioRolResponse response = new UsuarioRolResponse();

        response.setIdRol(userRol.getRol().getId());
        response.setIdUsuario(userRol.getUsuario().getId());
        response.setNombreRol(userRol.getRol().getNombre());

        return response;
    }

    public List<Usuario> findAll() throws Exception {

        List<Usuario> users = usuarioRepository.findAll();

        return users;
    }

    public Usuario getUserByUsrName(String usr_name) throws Exception {
        Usuario user = usuarioRepository.findByUsuario(usr_name);
        return user;
    }

    public boolean blankPwd(Integer id) throws Exception {
        Usuario user = findById(id);
        String date_s = "01/01/1900";
        Date dt = new SimpleDateFormat("dd/MM/yyyy").parse(date_s);
        try {
            String pwd = this.rndPwd();
            user.setPassword(bCryptPasswordEncoder.encode(pwd));
            user.setUltimoReset(dt);
            usuarioRepository.save(user);
            mailsrv.sendmail(pwd, user.getEmail());
        } catch (IOException | MessagingException e) {
            System.out.print("ERROR: " + e);
            return false;
        }
        return true;
    }

    private String rndPwd() {
        String pwd = null;
        int leftLimit = 48; // zero
        int rightLimit = 122; // zeta
        int targetStringLength = 10;
        Random random = new Random();
        pwd = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return pwd;
    }
}
