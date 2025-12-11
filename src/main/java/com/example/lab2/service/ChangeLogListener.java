package com.example.lab2.service; // поправь пакет под свой проект

import com.example.lab2.model.ChangeEvent;
import com.example.lab2.model.ChangeLog;
import com.example.lab2.repository.ChangeLogRepository;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ChangeLogListener {

    private final ChangeLogRepository changeLogRepository;

    public ChangeLogListener(ChangeLogRepository changeLogRepository) {
        this.changeLogRepository = changeLogRepository;
    }

    @JmsListener(
            destination = "changesTopic",
            containerFactory = "topicListenerFactory"
    )
    public void onMessage(ChangeEvent event) {
        ChangeLog log = new ChangeLog();
        log.setEntityName(event.getEntityName());
        log.setEntityId(event.getEntityId());
        log.setOperation(event.getOperation().name());
        log.setChangeTime(event.getChangeTime());
        log.setDetails(event.getDetails());

        changeLogRepository.save(log);
    }
}
