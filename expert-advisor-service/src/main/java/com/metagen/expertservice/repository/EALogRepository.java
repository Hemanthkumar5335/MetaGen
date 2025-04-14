package com.metagen.expertservice.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metagen.expertservice.entity.EALog;

public interface EALogRepository extends JpaRepository<EALog, Long> {
    List<EALog> findByUser(String user);
}
