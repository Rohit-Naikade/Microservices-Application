package com.microservices.order_service.config;

import com.microservices.order_service.client.InventoryClient;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.sql.Time;
import java.time.Duration;

@Configuration
public class RestClientConfig {

    @Value("${inventory.url}")
    private String inventoryServiceUrl;

    @Bean
    public InventoryClient inventoryClient(){
        RestClient restClient= RestClient.builder()
                .baseUrl(inventoryServiceUrl)
                //.requestFactory(getClientRequestFactory())
                .build();

        var restClientAdapter= RestClientAdapter.create(restClient);

        var httpServiceProxyFactory= HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServiceProxyFactory.createClient(InventoryClient.class);
    }

//    private ClientHttpRequestFactory getClientRequestFactory() {
//        RequestConfig config = RequestConfig.custom()
//                .setConnectTimeout(Timeout.ofSeconds(3)) // Connection timeout
//                .setResponseTimeout(Timeout.ofSeconds(3))   // Read timeout
//                .build();
//
//        CloseableHttpClient client = HttpClients.custom()
//                .setDefaultRequestConfig(config)
//                .build();
//
//        return new HttpComponentsClientHttpRequestFactory(client);
//    }
}
