package org.br.sistufbackend.model.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.br.sistufbackend.model.enums.YesNo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "sec_users")
public class SecUsuario implements UserDetails {

    @Id
    private String login;

    @JsonIgnore
    private String pswd;

    private String name;

    @Enumerated(EnumType.STRING)
    private YesNo active;

    @Column(name = "activation_code")
    private String activationCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "priv_admin")
    private YesNo privAdmin;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getListSecGrupoUsuario();
    }

    @Override
    public String getPassword() {
        return this.pswd;
    }

    @Override
    public String getUsername() {
        return this.login;
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
