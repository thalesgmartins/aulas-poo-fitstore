package br.com.empresa.controller;

import br.com.empresa.model.Categoria;
import br.com.empresa.service.CategoriaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Path("/categorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class categoriaController {

    @Inject
    private CategoriaService categoriaService;

    @GET
    public RestResponse<List<Categoria>> listarTodas() {
        return RestResponse.ok(categoriaService.listarTodas());
    }

    @GET
    @Path("/{id}")
    public RestResponse<Categoria> buscarPorId(@PathParam("id") Integer id) {
        return categoriaService.buscarPorId(id)
                .map(RestResponse::ok)
                .orElse(RestResponse.status(RestResponse.Status.NOT_FOUND));
    }

    @POST
    public RestResponse<Categoria> criar(Categoria categoria) {
        Categoria novaCategoria = categoriaService.create(categoria);
        return RestResponse.ResponseBuilder.ok(novaCategoria).status(RestResponse.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public RestResponse<Categoria> atualizar(@PathParam("id") Integer id, Categoria categoria) {
        return categoriaService.atualizar(id, categoria)
                .map(categoriaAtualizada -> RestResponse.ResponseBuilder.ok(categoriaAtualizada).build())
                .orElse(RestResponse.status(RestResponse.Status.NOT_FOUND));
    }

    @DELETE
    @Path("/{id}")
    public RestResponse<Void> deletar(@PathParam("id") Integer id) {
        boolean sucesso = categoriaService.deletar(id);

        if (sucesso) {
            return RestResponse.noContent();
        } else {
            return RestResponse.status(RestResponse.Status.NOT_FOUND);
        }
    }
}
