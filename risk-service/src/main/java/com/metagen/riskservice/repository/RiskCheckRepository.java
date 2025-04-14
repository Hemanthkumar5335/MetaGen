package com.metagen.riskservice.repository;

import com.metagen.riskservice.entity.RiskCheck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiskCheckRepository extends JpaRepository<RiskCheck, Long> {}
