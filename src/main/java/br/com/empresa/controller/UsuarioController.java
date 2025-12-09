package br.com.empresa.controller;

import br.com.empresa.UsuarioDetalhes;
import br.com.empresa.model.Usuario;
import br.com.empresa.service.UsuarioService;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import io.vertx.ext.auth.impl.jose.JWT;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.*;

@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioController {

    @Inject
    private SecurityContext securityContext;

    @Inject
    private UsuarioService usuarioService;

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    private String issuer;

    @GET
    @RolesAllowed({"ADMINISTRADOR"})
    public RestResponse<List<Usuario>> listarTodos() {
        return RestResponse.ResponseBuilder.ok(usuarioService.listarTodos()).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"ADMINISTRADOR"})
    public RestResponse<?> desativar(@PathParam("id") Long id) {
        usuarioService.desativar(id);
        return RestResponse.noContent();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"ADMINISTRADOR", "CLIENTE"})
    public UsuarioDetalhes detalhar(@PathParam("id") Long id) {
        return usuarioService.detalhar(id);
    }

    @PUT
    @RolesAllowed({"CLIENTE"})
    public RestResponse<?> editarUsuario(Usuario usuario) {
        usuarioService.editarUsuario(usuario);
        return RestResponse.ResponseBuilder.ok(usuario).build();
    }

    @POST
    @PermitAll
    public RestResponse<Usuario> criar(Usuario usuario) {
        Usuario newUsuario = usuarioService.create(usuario);
        return RestResponse.ResponseBuilder.ok(newUsuario).status(RestResponse.Status.CREATED).build();
    }

    @POST
    @Path("autenticar")
    @PermitAll
    public RestResponse<?> autenticar(Usuario usuario) {
        Usuario newUsuario = Usuario.find("email = ?1", usuario.email).firstResult();

        if(Objects.nonNull(newUsuario)) {
            boolean loginOK = BcryptUtil.matches(usuario.senha, newUsuario.senha);

            if (loginOK) {
                String token = Jwt.issuer(issuer)
                        .upn(newUsuario.email)
                        .groups(Collections.singleton(newUsuario.perfil.toString()))
                        .sign();

                NewCookie cookie = new NewCookie.Builder("jwt")
                        .value(token)
                        .path("/")
                        .httpOnly(true)
                        .maxAge(3600)
                        .build();

                return RestResponse.ResponseBuilder.ok("Logged").cookie(cookie).build();
            }
        }
        return RestResponse.ResponseBuilder.notFound().build();
    }
}
