package org.br.sistufbackend.model.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.br.sistufbackend.model.enums.StatusPagTesouro;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Optional;

@Data@AllArgsConstructor
public class GRUFilters {
    private Optional<String> status;
    private Optional<LocalDate> emissaoStart;
    private Optional<LocalDate> emissaoEnd;
    private Optional<LocalDate> vencimentoStart;
    private Optional<LocalDate> vencimentoEnd;
}
