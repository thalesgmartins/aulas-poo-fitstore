package br.com.empresa.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "usuarios")
public class Usuario extends PanacheEntity {
    @Column(name = "nome_completo", length = 300)
    public String nomeUsuario;
    public String endereco;
    public String cidade;
    public String uf;
    public String cep;
    public boolean ativo;
    public String email;
    @Column(name = "data_cadastro")
    public Date dataCadastro;
}
