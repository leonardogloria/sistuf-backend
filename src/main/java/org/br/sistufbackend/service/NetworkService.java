package org.br.sistufbackend.service;

import jakarta.servlet.http.HttpServletRequest;

import java.net.http.HttpRequest;

public interface NetworkService {
    String getIpFromRequest(HttpServletRequest  request);
}
