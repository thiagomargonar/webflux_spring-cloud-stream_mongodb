package com.curso.webflux.webflux.aluno.service;


import org.springframework.cloud.function.context.PollableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

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

    @PollableBean
    public Supplier<Flux<Message<String>>> supplier() {
        return () -> Flux.just(MessageBuilder.withPayload("ola").setHeader("teste","teste").build(),
                MessageBuilder.withPayload("adeus").setHeader("teste2","teste2").build());
    }

    @Bean
    public Function<Flux<Message<String>>, Flux<Message<String>>> uppercase() {
        return message -> {
            /*Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
            if (acknowledgment != null) {
                System.out.println("Acknowledgment provided");
                acknowledgment.acknowledge();
            }*/

            System.out.println("received **> " + message);
            return message.doOnNext(stringMessage -> {
                System.out.println(stringMessage);
            });
        };
    }
}
