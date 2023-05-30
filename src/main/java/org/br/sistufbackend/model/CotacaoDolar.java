package org.br.sistufbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "dolar")
@Data @AllArgsConstructor @Builder @NoArgsConstructor
public class CotacaoDolar {
    @Id
    @Column(name = "data")
    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate data;
    @Column(name = "ptax_compra")
    private BigDecimal compra;
    @Column(name = "ptax_venda")
    private BigDecimal venda;
}
