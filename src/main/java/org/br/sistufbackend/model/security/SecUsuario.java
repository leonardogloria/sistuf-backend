package org.br.sistufbackend.model.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.br.sistufbackend.model.Agencia;
import org.br.sistufbackend.model.enums.YesNo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data@Builder
@AllArgsConstructor@NoArgsConstructor
@Table(name = "sec_users")
public class SecUsuario implements UserDetails {

    @Id
    @Column(name = "login")
    private String id;

    @JsonIgnore
    @Column(name = "pswd")
    private String senha;

    @Column(name = "name")
    private String nome;

    @Enumerated(EnumType.STRING)
    private YesNo active;
    @Column(name = "email")
    private String email;
    @Column(name = "activation_code")
    private String codigoAtivacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "priv_admin")
    private YesNo isAdmin;

    @JsonIgnore
    @Column(name = "tipo_usr")
    private Character tipoUsr;

    private String cpf;
    private String nip;

    @Enumerated(EnumType.STRING)
    @Column(name = "change_pswd")
    private YesNo changePswd;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sec_users_groups",
            joinColumns = @JoinColumn(name = "login", referencedColumnName = "login"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "group_id")
    )
    private List<SecGrupoUsuario> listSecGrupoUsuario;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agencia_idagencia")
    private Agencia agencia;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getListSecGrupoUsuario();
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.id;
    }

    private boolean isAtiva() {
        return this.getActive().isTrue();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAtiva();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAtiva();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.getChangePswd().isTrue();
    }

    @Override
    public boolean isEnabled() {
        return this.isAtiva();
    }
}
