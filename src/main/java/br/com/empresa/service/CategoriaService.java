package br.com.empresa.service;

import br.com.empresa.model.Categoria;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CategoriaService {

    public List<Categoria> listarTodas() {
        return Categoria.listAll();
    }

    public Optional<Categoria> buscarPorId(Integer id) {
        return Categoria.findByIdOptional(id);
    }

    @Transactional
    public Categoria create(Categoria categoria) {
        categoria.persist();
        return categoria;
    }

    @Transactional
    public Optional<Categoria> atualizar(Integer id, Categoria categoriaAtualizada) {
        Optional<Categoria> categoriaOptional = Categoria.findByIdOptional(id);

        if (categoriaOptional.isEmpty()) {
            return Optional.empty();
        }

        Categoria categoriaExistente = categoriaOptional.get();

        categoriaExistente.descricao = categoriaAtualizada.descricao;
        return Optional.of(categoriaExistente);
    }

    @Transactional
    public boolean deletar(Integer id) {
        return Categoria.deleteById(id);
    }

}
