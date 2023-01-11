package fr.lernejo.search.api;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping("/api/games")
public class GameApiController {

    private final RestHighLevelClient client;

    public GameApiController(RestHighLevelClient client) {
        this.client = client;
    }

    @GetMapping
    public ArrayList<Object> callElasticsearch (@RequestParam(value="query") String query) throws IOException {
        ArrayList<Object> games = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest()
            .source(SearchSourceBuilder.searchSource().query(new QueryStringQueryBuilder(query)));

        this.client.search(searchRequest, RequestOptions.DEFAULT)
            .getHits().forEach(hit -> games.add(hit.getSourceAsMap()));
        return games;
    }
}
