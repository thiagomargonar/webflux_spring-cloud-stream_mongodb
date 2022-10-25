package com.curso.webflux.webflux.aluno.service;


import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.function.Supplier;

@Component
public class TesteProducerBean {

//    @Bean
//    public Supplier<String> supplier() {
//        return () -> {
//            System.out.println("Sending Hello World suplier");
//            return "Hello World - suplier";
//        };
//    }
//
//    @Bean
//    public Function<String, String> uppercase() {
//        return value -> {
//            System.out.println("received **> "+value);
//            return value.toUpperCase();
//        };
//    }

    @Bean
    public Supplier<Message<String>> supplier() {
        return () -> {
            System.out.println("Sending Hello World suplier");

            return MessageBuilder.withPayload("Hello World suplier")
                    .setHeader("bar", "baz")
                    .build();

        };
    }

    @Bean
    public Function<Message<String>, Message<String>> uppercase() {
        return message -> {
            Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
            if (acknowledgment != null) {
                System.out.println("Acknowledgment provided");
                acknowledgment.acknowledge();
            }

            System.out.println("received **> " + message);
            return message;
        };
    }
}
