package com.example.lc.service;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;

import java.util.concurrent.atomic.AtomicInteger;


public class MyListener implements MessageListener {

    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void onMessage(Message message) {
        try {
            String payload = (message instanceof TextMessage) ? ((TextMessage) message).getText() : message.toString();
            int attempt = counter.incrementAndGet();
            System.out.println("Received message attempt " + attempt + ": " + payload);

            System.out.println("Received message: " + payload);
            // Simulate processing failure for first 3 messages so they remain unacknowledged
            // In real scenario, you can decide to ack or not ack.
//            if (attempt <= 4) {
                System.out.println("Simulating failure, not acknowledging message — will be redelivered / eventually go to DLQ.");
                // Do NOT call message.acknowledge() to simulate failure.
                // Throw runtime exception to ensure message is considered not processed:
                throw new RuntimeException("Simulated processing error");
//            }
//            else {
//                // Successful processing: acknowledge so message is removed.
//                message.acknowledge();
//                System.out.println("Acknowledged message: " + payload);
//            }
        } catch (JMSException je) {
            throw new RuntimeException(je);
        }
    }
}

