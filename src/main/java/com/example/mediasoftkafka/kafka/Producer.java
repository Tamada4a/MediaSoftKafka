package com.example.mediasoftkafka.kafka;


import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class Producer {

    private final KafkaTemplate<String, String> template;

    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public void sendMessage(String message, String key){
        template.send("topic1", key, message);
    }
}
