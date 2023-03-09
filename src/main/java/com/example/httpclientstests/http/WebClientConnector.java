package com.example.httpclientstests.http;

import com.example.httpclientstests.Payment;
import com.example.httpclientstests.PaymentResponse;
import com.example.httpclientstests.ResponseHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientConnector {


    private final String middlewareUrl;
    private final String apiUrl;
    private final WebClient client;
    private final ResponseHandler responseHandler;

    public WebClientConnector(@Value("${integration.mw.url}") String middlewareUrl,
                              @Value("${integration.postPayment.api}") String apiUrl, ResponseHandler responseHandler, ObjectMapper objectMapper) {
        this.middlewareUrl = middlewareUrl;
        this.apiUrl = apiUrl;
        this.responseHandler = responseHandler;

        ExchangeStrategies jacksonStrategy = ExchangeStrategies.builder()
                .codecs(config -> {
                    config.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON));
                    config.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON));
                }).build();


        this.client = WebClient.builder().exchangeStrategies(jacksonStrategy).build();
    }

    public void postPayment(Payment payment){
        client.post().uri(middlewareUrl + apiUrl)
                .bodyValue(payment)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(PaymentResponse.class))
                .subscribe(responseHandler::handleResponse);
    }
}
