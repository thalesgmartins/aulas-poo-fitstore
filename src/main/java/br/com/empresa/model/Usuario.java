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

    @Column(length = 500)
    public String endereco;

    @Column(length = 100)
    public String cidade;

    @Column(length = 2)
    public String uf;

    @Column(length = 8)
    public String cep;

    @Column(length = 150)
    public String email;

    @Column(name = "data_cadastro")
    public Date dataCadastro;

    public boolean ativo;
    public String senha;

}
