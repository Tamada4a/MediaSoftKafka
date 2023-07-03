package com.example.mediasoftkafka.kafka;


import org.springframework.kafka.annotation.KafkaListener;


public class Consumer {
    @KafkaListener(topics = "topic1", containerFactory = "kafkaListenerContainerFactory", groupId = "${kafka.groupID1}")
    public void listen1(String input) {
        System.out.println("1) " + input);
    }


    @KafkaListener(topics = "topic1", containerFactory = "kafkaListenerContainerFactory", groupId = "${kafka.groupID2}")
    public void listen2(String input) {
        System.out.println("2) " + input);
    }
}
