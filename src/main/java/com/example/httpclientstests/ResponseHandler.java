package com.example.httpclientstests;

import com.example.httpclientstests.repositories.PaymentResponseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class ResponseHandler implements Serializable {

    private final AtomicInteger counter;
    private final PaymentResponseRepository repository;

    public ResponseHandler(PaymentResponseRepository repository) {
        this.repository = repository;
        this.counter = new AtomicInteger(0);
    }

    public void handleResponse(PaymentResponse paymentResponse){
        repository.save(paymentResponse);
        int count = counter.incrementAndGet();
        log.info("handled response number {}", count);
    }
}
