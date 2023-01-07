package fr.lernejo.fileinjector;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Paths;
import java.util.Map;

@SpringBootApplication
public class Launcher {

    public static void main(String[] args) {
        try (AbstractApplicationContext springContext = new AnnotationConfigApplicationContext(Launcher.class)) {
            try {
                // create object mapper instance
                ObjectMapper mapper = new ObjectMapper();

                // convert JSON file to map
                List<Map<String, Object>>  map = mapper.readValue(
                    Paths.get(args[0]).toFile(),
                    new TypeReference<List<Map<String,Object>>>(){}
                );
                Object[] mapArray = map.toArray();

                var rabbitTemplate = springContext.getBean(RabbitTemplate.class);
                // print map entries
                for (int i = 0; i < mapArray.length; i++) {
                    Map<String, Object> game = (Map<String, Object>) mapArray[i];
                    System.out.println(game);
                    int id = (int)game.get("id");
                    game.remove("id");
                    game.remove("freetogame_profile_url");
                    rabbitTemplate.convertAndSend("", "game_info", game.toString(), m -> {
                        m.getMessageProperties().getHeaders().put("game_id", id);
                        m.getMessageProperties().setContentType("application/json");
                        return m;
                    });
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println("Hello after starting Spring");
        }
    }
}
