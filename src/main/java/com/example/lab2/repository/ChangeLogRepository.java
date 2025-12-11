package com.example.lab2.repository;

import com.example.lab2.model.ChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangeLogRepository extends JpaRepository<ChangeLog, Long> {
}
