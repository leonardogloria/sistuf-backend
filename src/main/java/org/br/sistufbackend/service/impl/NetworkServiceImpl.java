package org.br.sistufbackend.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.br.sistufbackend.service.NetworkService;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;

@Service
public class NetworkServiceImpl implements NetworkService {
    @Override
    public String getIpFromRequest(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}
