package com.metagen.expertservice.service;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.metagen.expertservice.entity.EALog;
import com.metagen.expertservice.repository.EALogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EAService {

    private final EALogRepository repo;

    public void log(String user, String action, String symbol) {
        EALog log = new EALog(null, user, action, symbol, LocalDateTime.now());
        repo.save(log);
    }

    public List<EALog> getLogsByUser(String user) {
        return repo.findByUser(user);
    }
}
