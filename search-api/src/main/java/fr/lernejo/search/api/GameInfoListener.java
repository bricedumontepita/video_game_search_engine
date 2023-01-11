package fr.lernejo.search.api;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GameInfoListener {

    private final RestHighLevelClient client;

    public GameInfoListener(RestHighLevelClient client) {
        this.client = client;
    }

    @RabbitListener(queues = AmqpConfiguration.GAME_INFO_QUEUE)
    public void onMessage (String message, @Header("game_id") String gameId) {
        IndexRequest request = new IndexRequest("games").id(gameId);
        request.source(message, XContentType.JSON);

        try {
            IndexResponse response = this.client.index(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException | IOException e) {
            System.out.println("error index :" + e.toString());
        }
    }
}
