package com.example.producerconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebService(final SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }


    public void sendMessage(final String topicSuffix, final String payload) {
        messagingTemplate.convertAndSend(topicSuffix, payload);
    }
}


