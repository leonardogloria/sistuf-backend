package org.br.sistufbackend.model.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.br.sistufbackend.model.enums.StatusPagTesouro;

import java.time.LocalDateTime;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class Situacao {
    private StatusPagTesouro codigo;
    private LocalDateTime data;
}
