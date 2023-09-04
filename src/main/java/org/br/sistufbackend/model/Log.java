package org.br.sistufbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.br.sistufbackend.model.enums.LogAction;

import java.time.LocalDateTime;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Log {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="log_sequence")
    @SequenceGenerator(name="log_sequence", sequenceName="sc_log_id_seq",
            allocationSize = 1)
    private Long id;
    @Column(name = "inserted_date")
    private LocalDateTime data;
    @Column(name = "username")
    private String username;
    @Column(name = "application")
    private String aplicacao;
    @Column(name = "creator")
    private String criador;
    @Column(name = "ip_user")
    private String ip;
    @Column(name = "action")
    @Enumerated(EnumType.STRING)
    private LogAction acao;
    @Column(name = "description")
    private String descricao;

}
