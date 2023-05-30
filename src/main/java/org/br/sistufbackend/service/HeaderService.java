package org.br.sistufbackend.service;

import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Map;

public interface HeaderService {
    HttpHeaders getCustomHeaders(Map<String, String> headers);
}
