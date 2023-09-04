package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.model.Log;
import org.br.sistufbackend.repository.LogRepository;
import org.br.sistufbackend.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    LogRepository logRepository;
    @Override
    public void insert(Log log) {
        logRepository.save(log);
    }
}
