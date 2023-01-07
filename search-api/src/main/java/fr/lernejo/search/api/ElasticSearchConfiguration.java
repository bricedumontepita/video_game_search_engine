package fr.lernejo.search.api;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;

@Configuration
public class ElasticSearchConfiguration {

    //@Value("${elasticsearch.host}")
    private String host = "localhost";

    //@Value("${elasticsearch.port}")
    private int port = 9200;

    //@Value("${elasticsearch.username}")
    private String username = "elastic";

    //@Value("${elasticsearch.password}")
    private String password = "admin";

    @Bean
    public RestHighLevelClient client(
        @Value("${elasticsearch.host:'localhost'}") String host,
        @Value("${elasticsearch.port:9200}") int port,
        @Value("${elasticsearch.username:'elastic'}") String username,
        @Value("${elasticsearch.password:'admin'}") String password
        ) {

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
            new UsernamePasswordCredentials(username, password));

        RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(
                new HttpHost(host, port, "http"))
            .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                @Override
                public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                    return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                }
            }));
        return client;
    }
}
