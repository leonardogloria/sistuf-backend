package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.service.HeaderService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.Map;

@Service
public class HeaderServiceImpl implements HeaderService {
    @Override
    public HttpHeaders getCustomHeaders(Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::set);
        httpHeaders.setAccessControlExposeHeaders(Arrays.asList("*"));
        return httpHeaders;


    }
}
