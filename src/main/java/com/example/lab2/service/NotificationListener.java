package com.example.lab2.service;

import com.example.lab2.model.ChangeEvent;
import com.example.lab2.model.ChangeOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
public class NotificationListener {

    private final JavaMailSender mailSender;
    private final String notificationEmail;


    public NotificationListener(JavaMailSender mailSender,
                                @Value("${notifications.to}") String notificationEmail) {
        this.mailSender = mailSender;
        this.notificationEmail = notificationEmail;

        System.out.println(">>> notificationEmail = '" + notificationEmail + "'");
    }

    @JmsListener(
            destination = "changesTopic",
            containerFactory = "topicListenerFactory"
    )
    public void onMessage(ChangeEvent event) {
        if (!"FlightController".equals(event.getEntityName())) {
            return;
        }

        if (event.getOperation() != ChangeOperation.INSERT &&
                event.getOperation() != ChangeOperation.UPDATE) {
            return;
        }

        if (event.getCost() == null || event.getCost() <= 10_000.0) {
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("neutron-145@yandex.ru");
        message.setTo(notificationEmail);

        String subjOperation = (event.getOperation() == ChangeOperation.INSERT)
                ? "Добавлен"
                : "Изменён";

        message.setSubject(subjOperation + " дорогой полётный контроллер #" + event.getEntityId());

        StringBuilder text = new StringBuilder();
        text.append("Событие: ").append(event.getOperation()).append("\n");
        text.append("Сущность: ").append(event.getEntityName()).append("\n");
        text.append("ID: ").append(event.getEntityId()).append("\n");
        if (event.getCost() != null) {
            text.append("Стоимость: ").append(event.getCost()).append("\n");
        }
        if (event.getChangeTime() != null) {
            text.append("Время изменения: ").append(event.getChangeTime()).append("\n");
        }
        if (event.getDetails() != null) {
            text.append("\nПодробности:\n").append(event.getDetails());
        }

        message.setText(text.toString());

        mailSender.send(message);
    }
}