package fr.lernejo.search.api;

import jdk.jfr.Event;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.eql.EqlSearchResponse;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;

@Component
public class GameInfoListener {

    private RestHighLevelClient client;

    public GameInfoListener(RestHighLevelClient client) {
        this.client = client;
    }

    @RabbitListener(queues = "game_info")
    public void onMessage (String message, @Header("game_id") String gameId) {
        System.out.println(message + " " + gameId);

        IndexRequest request = new IndexRequest(
            "games",
            "doc",
            gameId
            );
        request.source("{ message : '" + message + "'}", XContentType.JSON);

        try {
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            System.out.println(response);
        } catch (ElasticsearchException | IOException e) {
            System.out.println("error index");
        }
    }
}
