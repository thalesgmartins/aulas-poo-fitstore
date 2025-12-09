package br.com.empresa.controller;

import br.com.empresa.model.Marca;
import br.com.empresa.service.MarcaService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;
import java.util.Optional;

@Path("/marcas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class marcaController {

    @Inject
    private MarcaService marcaService;

    @GET
    public RestResponse<List<Marca>> listarTodas() {
        return RestResponse.ok(marcaService.listarTodas());
    }

    @GET
    @Path("/{id}")
    public RestResponse<Marca> buscarPorId(@PathParam("id") Integer id) {
        return marcaService.buscarPorId(id)
                .map(RestResponse::ok)
                .orElse(RestResponse.status(RestResponse.Status.NOT_FOUND));
    }

    @POST
    @Transactional
    public RestResponse<Marca> criar(Marca marca) {
        Marca novaMarca = marcaService.create(marca);
        return RestResponse.ResponseBuilder.ok(novaMarca).status(RestResponse.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public RestResponse<Marca> atualizar(@PathParam("id") Integer id, Marca marca) {
        return marcaService.atualizar(id, marca)
                .map(marcaAtualizada -> RestResponse.ResponseBuilder.ok(marcaAtualizada).build())
                .orElse(RestResponse.status(RestResponse.Status.NOT_FOUND));
    }

    @DELETE
    @Path("/{id}")
    public RestResponse<Void> deletar(@PathParam("id") Integer id) {
        boolean sucesso = marcaService.deletar(id);

        if (sucesso) {
            return RestResponse.noContent();
        } else {
            return RestResponse.status(RestResponse.Status.NOT_FOUND);
        }
    }
}
