package fr.lernejo.fileinjector;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Paths;
import java.util.Map;

@SpringBootApplication
public class Launcher {

    public static void main(String[] args) throws IOException {
        try (AbstractApplicationContext springContext = new AnnotationConfigApplicationContext(Launcher.class)) {
            ArrayList<Game> listGame = new ArrayList<>();
            listGame = (new ObjectMapper()).readValue(Paths.get(args[0]).toFile(), new TypeReference<ArrayList<Game>>(){});
            var rabbitTemplate = springContext.getBean(RabbitTemplate.class);
            for (Game game : listGame) {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.convertAndSend("", "game_info", game, m -> {
                    m.getMessageProperties().getHeaders().put("game_id", game.id());
                    return m;
                });
            }
        }
    }
}
