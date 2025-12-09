package br.com.empresa.model;

import br.com.empresa.enums.PerfilUsuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "usuarios")
public class Usuario extends PanacheEntity {

    @Column(name = "nome_completo", length = 300)
    public String nomeCompleto;

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
    public LocalDate dataCadastro;

    public boolean ativo;

    public String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "perfil", nullable = false, length = 50)
    public PerfilUsuario perfil;
}
