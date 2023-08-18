package org.br.sistufbackend.model.security;

import jakarta.persistence.*;
import lombok.Data;
import org.br.sistufbackend.model.enums.YesNo;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "sec_groups")
@Data
public class UserGroup implements GrantedAuthority {

    @Id
    @Column(name = "group_id")
    private int id;

    private String description;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "visivel_capitania")
    private YesNo visivelCapitania;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "visivel_agencia")
    private YesNo visivelAgencia;

    @Override
    public String getAuthority() {
        return "ROLE_" + this.getId();
    }
}
