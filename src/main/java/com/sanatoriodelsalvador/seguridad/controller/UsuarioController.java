package com.sanatoriodelsalvador.seguridad.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.security.Keys;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;

import com.sanatoriodelsalvador.seguridad.entity.Usuario;
import com.sanatoriodelsalvador.seguridad.model.request.ChangePassword;
import com.sanatoriodelsalvador.seguridad.model.request.CreateUsuario;
import com.sanatoriodelsalvador.seguridad.model.request.UpdateUsuario;
import com.sanatoriodelsalvador.seguridad.model.response.UsuarioResponse;
import com.sanatoriodelsalvador.seguridad.model.response.UsuarioRolResponse;
import com.sanatoriodelsalvador.seguridad.model.response.UsuarioRolesResponse;
import com.sanatoriodelsalvador.seguridad.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/")
    public ResponseEntity<Object> Post(@RequestBody CreateUsuario usuario) throws Exception {
        UsuarioResponse user = usuarioService.saveUsuario(usuario);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId())
                .toUri();

        String usrId = String.valueOf(user.getId());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("user-id", usrId);

        HashMap<String, String> body = new HashMap<>();
        body.put("user-id", usrId);

        return ResponseEntity.created(location).headers(responseHeaders).body(usrId);
    }

    @PutMapping("/{id}")
    public void Put(@PathVariable Integer id, @RequestBody UpdateUsuario usuario) throws Exception {
        usuarioService.updateUsuario(id, usuario);
    }

    @GetMapping("/{id}")
    public UsuarioResponse Get(@PathVariable Integer id) throws Exception {
        return usuarioService.getUsuario(id);
    }

    @PostMapping("/{id}/roles/{rolId}")
    public ResponseEntity<Object> SetRol(@PathVariable Integer id, @PathVariable Integer rolId) throws Exception {

        UsuarioRolResponse rol = usuarioService.setRol(id, rolId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("").buildAndExpand(rol.getIdRol()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}/roles/{rolId}")
    public void DeleteRol(@PathVariable Integer id, @PathVariable Integer rolId) throws Exception {
        usuarioService.deleteRol(id, rolId);
    }

    @GetMapping("/{id}/roles")
    public UsuarioRolesResponse GetRoles(@PathVariable Integer id) throws Exception {
        return usuarioService.getRolesById(id);
    }

    @GetMapping("/{id}/roles/{rolId}")
    public UsuarioRolResponse GetRol(@PathVariable Integer id, @PathVariable Integer rolId) throws Exception {
        return usuarioService.getRol(id, rolId);
    }

    @PostMapping("/{id}/claves")
    public void UpdatePassword(@PathVariable Integer id, @RequestBody ChangePassword changePassword) throws Exception {
        changePassword.setId(id);
        usuarioService.changePassword(changePassword);
    }

    @GetMapping("/")
    public List<Usuario> Get() throws Exception {
        return usuarioService.findAll();
    }

    @PostMapping("/checkSignature")
    public boolean CheckSignature(@RequestBody Object t) throws Exception {

        // /* CODE WORKING!! */
        // String token_auth =
        // "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzbGZlbGlwcGEiLCJleHAiOjE1ODQ4MTg5NDksInJvbGUiOiJTVVBFUiBVU1VBUklPIn0.x9IhgO5n32Rgda7CYxUdixl4eEr0_WWlNGvDbIMscYdkvI7t_zLiD-6sTJZ2crBbzfm94XUHlthEhdMd9EfcIA";
        // // "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzbGZlbGlwcGEiLCJleHAiOjE1ODQ4MTg5NDksInJvbGUiOiJTVVBFUiBVU1VBUklPIn0.x9IhgO5n32Rgda7CYxUdixl4eEr0_WWlNGvDbIMscYdkvI7t_zLiD-6sTJZ2crBbzfm94XUHlthEhdMd9EfcIA";
        // String secret_signature = "ThisIsASrcretThisIsASrcretThisIsASrcretThisIsASrcretThisIsASrcretThisIsASrcretThisIsASrcretThisIsASrcret";
        // String encoded_signature =
        // Base64.getEncoder().encodeToString(secret_signature.getBytes());
        // // System.out.println("FAIL!");
        // // System.out.println("EX: " + e);
        // String[] token_split = token_auth.split("\\.");
        // Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        // byte[] byte_signature = secret_signature.getBytes(StandardCharsets.UTF_8);
        // try {
        // Jwts.parserBuilder().setSigningKey(secret_signature).build().parseClaimsJws(token_auth);
        // System.out.println("OK!");
        // // OK, we can trust this JWT
        // } catch (JwtException e) {
        // System.out.println("EX: " + e);
        // }
        return true;
    }

    @GetMapping("/findbyusername/{usrname}")
    public Usuario checkUser(@PathVariable String usrname) throws Exception {
        Usuario usr = usuarioService.getUserByUsrName(usrname);
        return usr;
    }

    @PostMapping("/blankpwd/{id}")
    public boolean CheckSignature(@PathVariable Integer id) throws Exception {
       return usuarioService.blankPwd(id);
    }

}
