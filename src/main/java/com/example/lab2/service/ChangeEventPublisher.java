package com.example.lab2.service;

import com.example.lab2.model.ChangeEvent;
import jakarta.jms.Topic;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChangeEventPublisher {

    private final JmsTemplate jmsTemplate;
    private final Topic changesTopic;

    public ChangeEventPublisher(JmsTemplate jmsTemplate, Topic changesTopic) {
        this.jmsTemplate = jmsTemplate;
        this.changesTopic = changesTopic;
    }

    public void publish(ChangeEvent event) {
        jmsTemplate.convertAndSend(changesTopic, event);
    }
}
