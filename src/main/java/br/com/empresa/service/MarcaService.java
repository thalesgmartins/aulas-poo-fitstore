package br.com.empresa.service;

import br.com.empresa.model.Marca;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MarcaService {
    public List<Marca> listarTodas() {
        return Marca.listAll();
    }

    public Optional<Marca> buscarPorId(Integer id) {
        return Marca.findByIdOptional(id);
    }

    @Transactional
    public Marca create(Marca marca) {
        marca.persist();
        return marca;
    }

    @Transactional
    public Optional<Marca> atualizar(Integer id, Marca marcaAtualizada) {
        Optional<Marca> marcaOptional = Marca.findByIdOptional(id);

        if (marcaOptional.isEmpty()) {
            return Optional.empty();
        }

        Marca marcaExistente = marcaOptional.get();

        marcaExistente.descricao = marcaAtualizada.descricao;
        return Optional.of(marcaExistente);
    }

    @Transactional
    public boolean deletar(Integer id) {
        return Marca.deleteById(id);
    }

}
