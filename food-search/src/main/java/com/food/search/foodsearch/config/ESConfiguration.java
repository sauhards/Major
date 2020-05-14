package com.food.search.foodsearch.config;



import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.elasticsearch.client.RestClientBuilder;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;

@Configuration
public class ESConfiguration {

    @Value("${elasticsearch.host}")
    private String ELASTIC_HOST;

    @Value("${elasticsearch.port}")
    private int ELASTIC_PORT;

    @Value("${elasticsearch.username}")
    private String ELASTIC_USERNAME;

    @Value("${elasticsearch.password}")
    private String ELASTIC_PWD;

    @Bean(destroyMethod = "close")
    public RestHighLevelClient client() {

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(ELASTIC_USERNAME, ELASTIC_PWD));

		RestClientBuilder builder = RestClient.builder(
		    new HttpHost(ELASTIC_HOST, ELASTIC_PORT, "https"))
			.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
		            @Override
		            public RequestConfig.Builder customizeRequestConfig(
		                    RequestConfig.Builder requestConfigBuilder) {
		                return requestConfigBuilder
		                    .setConnectTimeout(100000)
		                    .setSocketTimeout(600000);
		            }
		        })
		    .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
		        @Override
		        public HttpAsyncClientBuilder customizeHttpClient(
		                HttpAsyncClientBuilder httpClientBuilder) {
		            return httpClientBuilder
		                .setDefaultCredentialsProvider(credentialsProvider);
		        }
		    });

        RestHighLevelClient client = new RestHighLevelClient(builder);

        return client;
    }


}