package fr.lernejo.fileinjector;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

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

    public static void main(String[] args) {
        try (AbstractApplicationContext springContext = new AnnotationConfigApplicationContext(Launcher.class)) {
            ObjectMapper mapper = new ObjectMapper();
            ArrayList<Game> listGame;
            try {
                listGame = mapper.readValue(
                    Paths.get(args[0]).toFile(),
                    new TypeReference<ArrayList<Game>>(){}
                );
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }

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
